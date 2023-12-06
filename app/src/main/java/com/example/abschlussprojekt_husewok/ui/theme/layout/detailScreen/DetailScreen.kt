package com.example.abschlussprojekt_husewok.ui.theme.layout.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.LikedDislikedSwitch
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.LockedDurationDaysEdit
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.statics.LockedStatus
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function for the detail screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 * @param houseworkId The ID of the housework to be displayed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, viewModel: MainViewModel, houseworkId: String) {
    // Collect the houseworkList state from the view model
    val houseworkList by viewModel.houseworkList.collectAsStateWithLifecycle()

    // Find the housework with the matching ID
    val housework = houseworkList.find { housework ->
        housework.id == houseworkId
    } as Housework

    // Define mutable state variables for the input fields
    var title by remember { mutableStateOf(housework.title) }
    var task1 by remember { mutableStateOf(housework.task1) }
    var task2 by remember { mutableStateOf(housework.task2) }
    var task3 by remember { mutableStateOf(housework.task3) }
    var lockDurationDays by remember { mutableLongStateOf(housework.lockDurationDays) }
    var liked by remember { mutableStateOf(housework.isLiked) }

    // Get the scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Get the current context
    val context = LocalContext.current

    // Create coroutine scope for internet operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Create the detail screen layout using BasicScaffold
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "list") },
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false, false) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
            ),
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(state = rememberScrollState())
        ) {
            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)))

            // Display the housework image
            HouseworkImage(image = housework.image)

            // Input fields for title and tasks
            WideTextField(value = title, label = "Title") { value -> title = value }
            WideTextField(value = task1, label = "Task 1") { value -> task1 = value }
            WideTextField(value = task2, label = "Task 2") { value -> task2 = value }
            WideTextField(value = task3, label = "Task 3") { value -> task3 = value }

            // Switch for setting liked/disliked status
            LikedDislikedSwitch(liked = liked) { value -> liked = value }

            // Editable field for setting lock duration in days
            LockedDurationDaysEdit(
                lockDurationDays = lockDurationDays,
                addOnClick = { lockDurationDays = lockDurationDays!! + 1 },
                removeOnClick = { lockDurationDays = lockDurationDays!! - 1 }
            )

            // Show locked status
            LockedStatus(housework = housework)

            // Button for updating the task
            WideButton(text = "Update Task", icon = Icons.Outlined.Refresh, primary = true) {
                internetScope.launch {
                    DetailScreenFunctions.updateTask(
                        viewModel,
                        navController,
                        context,
                        housework,
                        title,
                        task1,
                        task2,
                        task3,
                        lockDurationDays,
                        liked
                    )
                }
            }

            // Button for deleting the task if it is not default
            if (!housework.default) {
                WideButton(text = "Delete Task", icon = Icons.Outlined.Delete, primary = false) {
                    internetScope.launch {
                        DetailScreenFunctions.deleteTask(
                            viewModel,
                            navController,
                            houseworkId
                        )
                    }
                }
            }

            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)))
        }
    }
}