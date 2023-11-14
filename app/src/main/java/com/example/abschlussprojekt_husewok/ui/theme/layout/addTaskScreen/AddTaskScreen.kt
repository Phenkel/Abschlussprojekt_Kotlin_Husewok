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
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.components.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.LikedDislikedSwitch
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.LockedDurationDaysEdit
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A composable function that represents the screen for adding a new task.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing and updating housework data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, viewModel: MainViewModel) {
    // Create mutable state variables for the task details
    var title by remember { mutableStateOf("") }
    var task1 by remember { mutableStateOf("") }
    var task2 by remember { mutableStateOf("") }
    var task3 by remember { mutableStateOf("") }
    var lockDurationDays by remember { mutableLongStateOf(7) }
    var liked by remember { mutableStateOf(true) }

    // Define the scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create a coroutine scope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Compose the add task screen UI using BasicScaffold
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "list") },
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false) }
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
            Spacer(
                modifier = Modifier.height(
                    calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                )
            )

            // Display the housework image
            HouseworkImage(image = R.drawable.img_placeholder)

            // Display the title text field
            WideTextField(value = title, label = "Title") { value -> title = value }

            // Display the task 1 text field
            WideTextField(value = task1, label = "Task 1") { value -> task1 = value }

            // Display the task 2 text field
            WideTextField(value = task2, label = "Task 2") { value -> task2 = value }

            // Display the task 3 text field
            WideTextField(value = task3, label = "Task 3") { value -> task3 = value }

            // Display the liked/disliked switch
            LikedDislikedSwitch(liked = liked) { value -> liked = value }

            // Display the locked duration days editor
            LockedDurationDaysEdit(
                lockDurationDays = lockDurationDays,
                addOnClick = { lockDurationDays++ },
                removeOnClick = { lockDurationDays-- }
            )

            // Display the "Create Task" button
            WideButton(text = "Create Task", icon = Icons.Outlined.AddCircle, primary = true) {
                firebaseScope.launch {
                    // Add the new task to Firebase
                    viewModel.upsertHouseworkFirebase(
                        Housework(
                            image = R.drawable.img_placeholder,
                            title = title,
                            task1 = task1,
                            task2 = task2,
                            task3 = task3,
                            isLiked = liked,
                            lockDurationDays = lockDurationDays,
                            lockExpirationDate = "",
                            default = false,
                            id = "default"
                        )
                    )

                    // Update the housework list
                    viewModel.updateHouseworkList()
                }

                // Navigate back to the list screen
                navController.popBackStack("list", false)
            }

            Spacer(
                modifier = Modifier.height(
                    calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                )
            )
        }
    }
}