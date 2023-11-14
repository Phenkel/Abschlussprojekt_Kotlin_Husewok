package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A composable function that represents the list screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing and updating housework data.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(navController: NavController, viewModel: MainViewModel) {
    // Update the housework list when the composable is first composed
    LaunchedEffect(Unit) {
        viewModel.updateHouseworkList()
    }

    // Collect the housework list state from the view model
    val houseworkList by viewModel.houseworkList.collectAsStateWithLifecycle()

    // Define the scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Get the current context
    val context = LocalContext.current

    // Create a coroutine scope for refreshing the housework list
    val refreshScope = rememberCoroutineScope()

    // State variable for refreshing the housework list
    var refreshing by remember { mutableStateOf(false) }

    // Refresh the housework list
    fun refresh() = refreshScope.launch {
        refreshing = true

        // Update the housework list
        viewModel.updateHouseworkList()

        // Simulate a delay for refreshing
        delay(500)

        // Show a success toast if the housework list is not empty
        if (houseworkList.isNotEmpty()) {
            MotionToasts.success(
                title = "Success",
                message = "Housework list found",
                activity = context as Activity,
                context = context
            )
        } else {
            // Show an error toast if the housework list is empty
            MotionToasts.error(
                title = "Error",
                message = "No housework list found. Please reload again",
                activity = context as Activity,
                context = context
            )
        }

        refreshing = false
    }

    // Create a pull refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    // Compose the list screen UI using BasicScaffold
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "home") },
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    calcDp(percentage = 0.05f, dimension = CalcSizes.Dimension.Height)
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                item {
                    // Display the sorting buttons
                    SortingButtons(
                        byLikedOnClick = {
                            viewModel.sortHouseworkList("Liked")
                            MotionToasts.info(
                                title = "Sorted by liked",
                                message = "Liked tasks will be shown first",
                                activity = context as Activity,
                                context = context
                            )
                        },
                        byLockedOnClick = {
                            viewModel.sortHouseworkList("Locked")
                            MotionToasts.info(
                                title = "Sorted by locked",
                                message = "Locked tasks will be shown first",
                                activity = context as Activity,
                                context = context
                            )
                        },
                        byRandomOnClick = {
                            viewModel.sortHouseworkList("Random")
                            MotionToasts.info(
                                title = "Sorted by random",
                                message = "Have fun searching",
                                activity = context as Activity,
                                context = context
                            )
                        }
                    )
                }
                items(houseworkList) { houseworkItem ->
                    // Display the swipeable housework list card
                    SwipeableHouseworkListCard(housework = houseworkItem, houseworkList = houseworkList) {
                        viewModel.updateDetailedHousework(houseworkItem)
                        navController.navigate("detail")
                    }
                }
                item {
                    // Display the "Add new task" button
                    WideButton(text = "Add new task", icon = Icons.Outlined.Add, primary = true) {
                        navController.navigate("addTask")
                    }
                }
            }

            // Display the pull refresh indicator
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}