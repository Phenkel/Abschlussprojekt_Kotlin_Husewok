package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.cards.HomeScreenCard
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function for the home screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the current user, active housework, and loading states from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val activeHousework by viewModel.activeHousework.collectAsStateWithLifecycle()
    val firstLoading by viewModel.firstLoading.collectAsStateWithLifecycle()

    // Retrieve the current context
    val context = LocalContext.current

    // Set up coroutine scopes for internet operations and UI updates
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val mainScope = MainScope()

    // Set up scroll state and refresh scope for pull-to-refresh functionality
    val scrollState = rememberScrollState()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    // Function to handle refresh action
    fun refresh() = refreshScope.launch {
        refreshing = true
        HomeScreenFunctions.loadHousework(viewModel, navController, context, internetScope)
        refreshing = false
    }

    // Set up pull-to-refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    // Load housework data if it's the first loading
    if (firstLoading) {
        LaunchedEffect(Unit) {
            HomeScreenFunctions.loadHousework(viewModel, navController, context, internetScope)
            viewModel.firstLoaded()
        }
    }

    // Compose the home screen layout
    BasicScaffold(
        topBar = { NoNavigationTopAppBar() },
        bottomBar = {
            AnimatedBottomAppBar(navController, 0, true, false, false, false)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(1f)
            ) {
                // Render the active housework card if available
                activeHousework?.let { hw ->
                    HomeScreenCard(
                        skipButtonEnabled = (user?.skipCoins ?: 0) > 0,
                        skipButtonOnClick = {
                            HomeScreenFunctions.skipButton(navController, viewModel, activeHousework)
                        },
                        doneButtonOnClick = {
                            mainScope.launch {
                                HomeScreenFunctions.doneButton(
                                    viewModel, navController, context, internetScope, hw, user
                                )
                            }
                        },
                        fabOnClick = {
                            HomeScreenFunctions.infoButton(context, hw)
                        },
                        iconButtonOnClick = {
                            mainScope.launch {
                                HomeScreenFunctions.favoriteButton(viewModel, internetScope, hw)
                            }
                        },
                        housework = hw
                    )
                }
            }

            // Pull-to-refresh indicator
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}