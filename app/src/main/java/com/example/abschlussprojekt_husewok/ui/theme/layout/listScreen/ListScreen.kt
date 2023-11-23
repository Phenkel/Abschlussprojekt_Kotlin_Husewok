package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.SortingButtons
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.cards.SwipeableHouseworkListCard
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function for the list screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ListScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the houseworkList state from the viewModel
    val houseworkList by viewModel.houseworkList.collectAsStateWithLifecycle()

    // Set up scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Retrieve the current context
    val context = LocalContext.current

    // Set up coroutine scope for internet operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Set up main coroutine scope
    val mainScope = MainScope()

    // Set up coroutine scope for pull-to-refresh
    val refreshScope = rememberCoroutineScope()

    // State variable for refreshing state
    var refreshing by remember { mutableStateOf(false) }

    // Function for refreshing the housework list
    fun refresh() = refreshScope.launch {
        refreshing = true
        ListScreenFunctions.refresh(
            viewModel,
            internetScope,
            context,
            houseworkList
        )
        refreshing = false
    }

    // Create the pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    // Compose the list screen layout
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "home") },
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false, false) }
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
                // Sorting buttons
                item {
                    SortingButtons(
                        byLikedOnClick = {
                            mainScope.launch { ListScreenFunctions.sortByLiked(
                                viewModel, internetScope, context) }
                        },
                        byLockedOnClick = {
                            mainScope.launch { ListScreenFunctions.sortByLocked(
                                viewModel, internetScope, context) }
                        },
                        byRandomOnClick = {
                            mainScope.launch { ListScreenFunctions.sortByRandom(
                                viewModel, internetScope, context) }
                        }
                    )
                }

                // "Add new task" button
                item {
                    WideButton(text = "Add new task", icon = Icons.Outlined.Add, primary = true) {
                        navController.navigate("addTask")
                    }
                }

                // Housework list
                items(houseworkList) { houseworkItem ->
                    SwipeableHouseworkListCard(housework = houseworkItem, houseworkList = houseworkList) {
                        navController.navigate("detail/${houseworkItem.id}")
                    }
                }
            }

            // Pull-to-refresh indicator
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}