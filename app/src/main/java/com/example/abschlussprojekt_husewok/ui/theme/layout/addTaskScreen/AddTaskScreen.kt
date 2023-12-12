package com.example.abschlussprojekt_husewok.ui.theme.layout.addTaskScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
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
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.LikedDislikedSwitch
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.LockedDurationDaysEdit
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
 * Composable function for the add task screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, viewModel: MainViewModel) {
    // State variables for input fields
    var title by remember { mutableStateOf("") }
    var task1 by remember { mutableStateOf("") }
    var task2 by remember { mutableStateOf("") }
    var task3 by remember { mutableStateOf("") }
    var lockDurationDays by remember { mutableLongStateOf(7) }
    var liked by remember { mutableStateOf(true) }

    // Set up scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Retrieve the current context
    val context = LocalContext.current

    // Set up coroutine scopes for internet operations and UI updates
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val mainScope = MainScope()

    // Compose the add task screen layout
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
            HouseworkImage(image = R.drawable.img_placeholder)

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
                addOnClick = { lockDurationDays++ },
                removeOnClick = { lockDurationDays-- }
            )

            // Button for creating the task
            WideButton(text = "Create Task", icon = Icons.Outlined.AddCircle, primary = true) {
                internetScope.launch {
                    AddTaskScreenFunctions.addTask(
                        viewModel,
                        navController,
                        mainScope,
                        context,
                        title,
                        task1,
                        task2,
                        task3,
                        liked,
                        lockDurationDays
                    )
                }
            }

            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)))
        }
    }
}