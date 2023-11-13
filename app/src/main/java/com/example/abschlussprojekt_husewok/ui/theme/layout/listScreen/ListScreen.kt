package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Composable function to display the list screen.
 *
 * @param navController The NavController used for navigation.
 * @param viewModel The MainViewModel used for data access.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(navController: NavController, viewModel: MainViewModel) {
    // Update the housework list
    LaunchedEffect(Unit) {
        viewModel.updateHouseworkList()
    }

    // Collect the housework list state from the view model
    val houseworkList by viewModel.houseworkList.collectAsStateWithLifecycle()

    // Define scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Get the current context
    val context = LocalContext.current

    // Define the refresh functionality
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.updateHouseworkList()
        delay(500)
        MotionToasts.info(
            title = "Refreshed",
            message = "All houseworks are refreshed",
            activity = context as Activity,
            context = context
        )
        refreshing = false
    }

    // Define the pull-to-refresh state
    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)


    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        topBar = { BasicTopAppBar(scrollBehavior) },
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                // Display the list of housework items
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
                        .pullRefresh(state)
                ) {
                    // Display the sorting buttons
                    item {
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
                                viewModel.sortHouseworkList("Locked")
                                MotionToasts.info(
                                    title = "Sorted by random",
                                    message = "Have fun searching",
                                    activity = context as Activity,
                                    context = context
                                )
                            }
                        )
                    }

                    // Display the housework items
                    items(houseworkList) { houseworkItem ->
                        SwipeableHouseworkListCard(housework = houseworkItem, houseworkList = houseworkList) {
                            viewModel.updateDetailedHousework(houseworkItem)
                            navController.navigate("detail")
                        }
                    }

                    // Display the "Add new task" button
                    item {
                        WideButton(text = "Add new task", icon = Icons.Outlined.Add, primary = true) {
                            navController.navigate("addTask")
                        }
                    }
                }

                // Display the pull-to-refresh indicator
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }
        }
    )
}