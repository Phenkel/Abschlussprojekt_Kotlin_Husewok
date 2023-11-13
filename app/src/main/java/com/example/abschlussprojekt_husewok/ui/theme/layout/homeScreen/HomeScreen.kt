package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.cards.CardWithAnimatedBorder
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Composable function to display the home screen.
 *
 * @param navController The NavController used for navigation.
 * @param viewModel The MainViewModel used for data access.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the current user state from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    // Collect the active housework state from the view model
    val activeHousework by viewModel.activeHousework.collectAsStateWithLifecycle()

    // Define mutable state variables for loading and progress
    var loading by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    // Check if active housework is null
    if (activeHousework == null) {
        LaunchedEffect(Unit) {
            loading = true

            // Simulate loading progress
            for (i in 1..100) {
                if (i == 33) viewModel.updateHouseworkList()
                if (i == 66) viewModel.getActiveHousework()

                // Check if active housework is not null
                if (activeHousework != null) loading = false

                // Update progress value
                progress = i.toFloat() / 100

                delay(30)
            }

            loading = false
        }
    } else {
        loading = false
    }

    // Create a CoroutineScope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Get the current context
    val context = LocalContext.current

    // Create a scroll state for the vertical scrollable content
    val scrollState = rememberScrollState()

    // Create a refresh scope for the pull-to-refresh functionality
    val refreshScope = rememberCoroutineScope()

    // Define a mutable state variable for refreshing
    var refreshing by remember { mutableStateOf(false) }

    // Function to handle refresh action
    fun refresh() = refreshScope.launch {
        refreshing = true

        // Update housework list and active housework
        viewModel.updateHouseworkList()
        delay(250)
        viewModel.getActiveHousework()
        delay(250)

        // Show refresh success toast
        MotionToasts.info(
            title = "Refreshed",
            message = "Active housework is refreshed",
            activity = context as Activity,
            context = context
        )

        refreshing = false
    }

    // Create a state for the pull-to-refresh indicator
    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        topBar = { NoNavigationTopAppBar() },
        bottomBar = { AnimatedBottomAppBar(navController, 0, true, false, false) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
                    .pullRefresh(state)
                    .verticalScroll(scrollState),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(1f)
                ) {
                    // Display the active housework card if it exists
                    activeHousework?.let {
                        CardWithAnimatedBorder(
                            content = {
                                HomescreenCard(
                                    skipButtonEnabled = (user?.skipCoins ?: 0) > 0,
                                    skipButtonOnClick = {
                                        firebaseScope.launch {
                                            viewModel.updateActiveHousework(true)
                                            viewModel.updateUserSkipCoins(false)
                                        }
                                    },
                                    doneButtonOnClick = {
                                        firebaseScope.launch {
                                            viewModel.updateActiveHousework(true)
                                            viewModel.updateUserSkipCoins(true)
                                        }
                                    },
                                    fabOnClick = {
                                        // TODO: Implement fabOnClick functionality
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
                                            }
                                        }
                                    },
                                    housework = it
                                )
                            },
                            liked = it.isLiked
                        )
                    }
                }
                // Display the loading indicator if loading is true
                if (loading) {
                    ConstraintLayout(
                        modifier = Modifier
                            .height(calcDp(percentage = 1f, dimension = CalcSizes.Dimension.Height))
                            .width(calcDp(percentage = 1f, dimension = CalcSizes.Dimension.Width))
                    ) {
                        val (indicator) = createRefs()
                        CircularProgressIndicator(
                            progress = progress,
                            color = Orange80,
                            modifier = Modifier
                                .width(calcDp(percentage = 0.25f, dimension = CalcSizes.Dimension.Full))
                                .height(calcDp(percentage = 0.25f, dimension = CalcSizes.Dimension.Height))
                                .constrainAs(indicator) {
                                    centerTo(parent)
                                }
                        )
                    }
                }

                // Display the pull-to-refresh indicator
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }
        }
    )
}