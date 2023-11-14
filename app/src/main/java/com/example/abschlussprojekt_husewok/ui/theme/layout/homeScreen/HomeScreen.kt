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
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.progressIndicator.FullScreenProgressIndicator
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A composable function that represents the home screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing and updating data.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the current user state from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    // Collect the active housework state from the view model
    val activeHousework by viewModel.activeHousework.collectAsStateWithLifecycle()

    // Collect the housework list state from the view model
    val houseworkList by viewModel.houseworkList.collectAsStateWithLifecycle()

    // State variables for loading and progress
    var loading by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    // Get the current context
    val context = LocalContext.current

    // Check if there is no active housework
    if (activeHousework == null) {
        LaunchedEffect(Unit) {
            loading = true

            for (i in 1..100) {
                if (i == 33) viewModel.updateHouseworkList()
                if (i == 66) viewModel.getActiveHousework()

                progress = i.toFloat() / 100
                delay(10)
            }

            if (activeHousework?.isLocked() == true) {
                var unlockedHousework = false

                houseworkList.forEach { housework ->
                    if (!housework.isLocked()) {
                        unlockedHousework = true
                    }
                }

                if (unlockedHousework) {
                    // TODO: Navigate to Game screen
                } else {
                    MotionToasts.info(
                        title = "All Done",
                        message = "There is no housework left",
                        activity = context as Activity,
                        context = context
                    )
                }
            }

            loading = false
        }
    } else {
        loading = false
    }

    // Create a coroutine scope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Create a scroll state for the scrollable content
    val scrollState = rememberScrollState()

    // Create a coroutine scope for refreshing
    val refreshScope = rememberCoroutineScope()

    // State variable for refreshing
    var refreshing by remember { mutableStateOf(false) }

    /**
     * Refresh the data
     */
    fun refresh() = refreshScope.launch {
        refreshing = true

        // Update the housework list
        viewModel.updateHouseworkList()
        delay(250)

        // Update the active housework
        viewModel.getActiveHousework()
        delay(250)

        if (activeHousework?.isLocked() == true) {
            var unlockedHousework = false

            houseworkList.forEach { housework ->
                if (!housework.isLocked()) {
                    unlockedHousework = true
                }
            }

            if (unlockedHousework) {
                // TODO: Navigate to Game screen
            } else {
                MotionToasts.info(
                    title = "All Done",
                    message = "There is no housework left",
                    activity = context as Activity,
                    context = context
                )
            }
        } else if (activeHousework != null) {
            MotionToasts.success(
                title = "Success",
                message = "Active housework found",
                activity = context as Activity,
                context = context
            )
        } else {
            MotionToasts.error(
                title = "Error",
                message = "No active housework found.\nPlease reload again",
                activity = context as Activity,
                context = context
            )
        }

        refreshing = false
    }

    // Create a pull refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

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
                activeHousework?.let {
                    HomeScreenCard(
                        skipButtonEnabled = (user?.skipCoins ?: 0) > 0,
                        skipButtonOnClick = {
                            firebaseScope.launch {
                                viewModel.updateActiveHousework(true)
                                viewModel.updateUserTasksAndSkipCoins(false)
                            }
                        },
                        doneButtonOnClick = {
                            var lockedHousework: Housework = activeHousework as Housework
                            activeHousework?.let { housework ->
                                lockedHousework = Housework(
                                    image = housework.image,
                                    title = housework.title,
                                    task1 = housework.task1,
                                    task2 = housework.task2,
                                    task3 = housework.task3,
                                    isLiked = housework.isLiked,
                                    lockDurationDays = housework.lockDurationDays,
                                    lockExpirationDate = housework.updateLockExpirationDate(),
                                    default = housework.default,
                                    id = housework.id
                                )
                            }
                            firebaseScope.launch {
                                viewModel.updateUserTasksAndSkipCoins(true)
                                viewModel.upsertHouseworkFirebase(lockedHousework)
                                viewModel.updateHouseworkList()
                                viewModel.updateActiveHousework(true)
                            }
                        },
                        fabOnClick = {
                            MotionToasts.info(
                                title = "${it.task1}\n${it.task2}\n${it.task3}",
                                message = it.title,
                                activity = context as Activity,
                                context = context
                            )
                        },
                        iconButtonOnClick = {
                            if (it.title != "All done") {
                                firebaseScope.launch {
                                    viewModel.upsertHouseworkFirebase(Housework(
                                        image = it.image,
                                        title = it.title,
                                        task1 = it.task1,
                                        task2 = it.task2,
                                        task3 = it.task3,
                                        isLiked = !it.isLiked,
                                        lockDurationDays = it.lockDurationDays,
                                        lockExpirationDate = it.lockExpirationDate,
                                        default = it.default,
                                        id = it.id
                                    ))
                                    viewModel.updateHouseworkList()
                                    viewModel.getActiveHousework()
                                }
                            }
                        },
                        housework = it
                    )
                }
            }

            // Display the loading indicator if loading is true
            if (loading) {
                FullScreenProgressIndicator(progress, Purple80)
            }

            // Display the pull refresh indicator
            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}