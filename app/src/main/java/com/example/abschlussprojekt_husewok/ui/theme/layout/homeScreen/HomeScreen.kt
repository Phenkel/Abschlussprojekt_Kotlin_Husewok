package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import android.app.Activity
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.progressIndicator.FullScreenProgressIndicator
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A composable function that represents the home screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing and updating data.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the state values from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val activeHousework by viewModel.activeHousework.collectAsStateWithLifecycle()
    val houseworkList by viewModel.houseworkList.collectAsStateWithLifecycle()
    val bored by viewModel.bored.collectAsStateWithLifecycle()
    val joke by viewModel.joke.collectAsStateWithLifecycle()

    // State variables for loading and progress
    var loading by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    // Get the current context
    val context = LocalContext.current

    // Create coroutine scopes for Firebase and API operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val apiScope = CoroutineScope(Dispatchers.IO)

    // Create a main coroutine scope
    val mainScope = MainScope()

    // Create a scroll state for the scrollable content
    val scrollState = rememberScrollState()

    // Create a coroutine scope for refreshing the screen
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    // Refresh the screen
    fun refresh() = refreshScope.launch {
        refreshing = true
        HomeScreenFunctions.refresh(viewModel, context, firebaseScope, activeHousework, houseworkList)
        refreshing = false
    }

    // Create a pull refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    // Perform initial loading if active housework is null or housework list is empty
    if (activeHousework == null || houseworkList.isEmpty()) {
        LaunchedEffect(Unit) {
            loading = true
            withContext(NonCancellable) {
                for (i in 1..100) {
                    if (i == 50) firebaseScope.launch { viewModel.updateHouseworkList() }
                    if (i == 100) firebaseScope.launch { viewModel.getActiveHousework() }
                    progress = i.toFloat() / 100
                    delay(10)
                }
            }
            loading = false
        }
    }
    // Perform actions when active housework is locked
    else if (activeHousework?.isLocked() == true) {
        LaunchedEffect(Unit) {
            HomeScreenFunctions.onCreate(viewModel, context, firebaseScope, houseworkList)
        }
    }
    // Perform actions when active housework is "All done"
    else if (activeHousework?.title == "All done") {
        HomeScreenFunctions.allDoneFound(viewModel, context, firebaseScope, houseworkList)
    }

    // Compose the home screen UI using BasicScaffold
    BasicScaffold(
        topBar = { NoNavigationTopAppBar() },
        bottomBar = { AnimatedBottomAppBar(navController, 0, true, false, false) }
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
                activeHousework?.let { hw ->
                    HomeScreenCard(
                        skipButtonEnabled = (user?.skipCoins ?: 0) > 0,
                        skipButtonOnClick = {
                            HomeScreenFunctions.skipButton(viewModel, firebaseScope, activeHousework)
                        },
                        doneButtonOnClick = {
                            HomeScreenFunctions.doneButton(
                                viewModel,
                                context,
                                firebaseScope,
                                apiScope,
                                mainScope,
                                hw,
                                user,
                                bored,
                                joke
                            )
                        },
                        fabOnClick = {
                            MotionToasts.info(
                                title = "${hw.task1}\n${hw.task2}\n${hw.task3}",
                                message = hw.title,
                                activity = context as Activity,
                                context = context
                            )
                        },
                        iconButtonOnClick = {
                            HomeScreenFunctions.iconButton(viewModel, firebaseScope, hw)
                        },
                        housework = hw
                    )
                }
            }
            if (loading) {
                FullScreenProgressIndicator(progress, Purple80)
            }
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}