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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.LikedDislikedSwitch
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.LockedDurationDaysEdit
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A composable function that represents the detail screen for a housework item.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing and updating housework data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the detailed housework state from the view model
    val housework by viewModel.detailedHousework.collectAsStateWithLifecycle()

    // Create mutable state variables for the housework details
    var title by remember { mutableStateOf(housework?.title) }
    var task1 by remember { mutableStateOf(housework?.task1) }
    var task2 by remember { mutableStateOf(housework?.task2) }
    var task3 by remember { mutableStateOf(housework?.task3) }
    var lockDurationDays by remember { mutableStateOf(housework?.lockDurationDays) }
    var liked by remember { mutableStateOf(housework?.isLiked) }

    // Define the scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create a coroutine scope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Compose the detail screen UI using BasicScaffold
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
            housework?.let { housework ->
                HouseworkImage(image = housework.image)
            }

            // Display the title text field
            title?.let {
                WideTextField(value = it, label = "Title") { value -> title = value }
            }

            // Display the task 1 text field
            task1?.let {
                WideTextField(value = it, label = "Task 1") { value -> task1 = value }
            }

            // Display the task 2 text field
            task2?.let {
                WideTextField(value = it, label = "Task 2") { value -> task2 = value }
            }

            // Display the task 3 text field
            task3?.let {
                WideTextField(value = it, label = "Task 3") { value -> task3 = value }
            }

            // Display the liked/disliked switch
            liked?.let {
                LikedDislikedSwitch(liked = it) { value -> liked = value }
            }

            // Display the locked duration days editor
            lockDurationDays?.let {
                LockedDurationDaysEdit(
                    lockDurationDays = it,
                    addOnClick = { lockDurationDays = lockDurationDays!! + 1 },
                    removeOnClick = { lockDurationDays = lockDurationDays!! - 1 }
                )
            }

            // Display the locked status
            housework?.let {
                LockedStatus(housework = it)
            }

            // Display the "Update Task" button
            WideButton(text = "Update Task", icon = Icons.Outlined.Refresh, primary = true) {
                firebaseScope.launch {
                    // Update the housework item in Firebase
                    viewModel.upsertHouseworkFirebase(
                        Housework(
                            image = housework?.image ?: R.drawable.img_placeholder,
                            title = title ?: "",
                            task1 = task1 ?: "",
                            task2 = task2 ?: "",
                            task3 = task3 ?: "",
                            isLiked = liked ?: true,
                            lockDurationDays = lockDurationDays ?: 1,
                            lockExpirationDate = housework?.lockExpirationDate ?: "",
                            default = housework?.default ?: true,
                            id = housework?.id ?: "default"
                        )
                    )

                    // Update the housework list
                    viewModel.updateHouseworkList()
                }

                // Navigate back to the list screen
                navController.popBackStack("list", false)
            }

            // Display the "Delete Task" button if the housework item is not a default task
            if (housework?.default == false) {
                WideButton(text = "Delete Task", icon = Icons.Outlined.Delete, primary = false) {
                    firebaseScope.launch {
                        // Delete the housework item from Firebase
                        viewModel.deleteHousework(housework?.id ?: "")

                        // Update the housework list
                        viewModel.updateHouseworkList()
                    }

                    // Navigate back to the list screen
                    navController.popBackStack("list", false)
                }
            }

            Spacer(
                modifier = Modifier.height(
                    calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                )
            )
        }
    }
}