package com.example.abschlussprojekt_husewok.ui.theme.layout.addTaskScreen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.components.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.LikedDislikedSwitch
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.LockedDurationDaysEdit
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function to display the add task screen.
 *
 * @param navController The NavController used for navigation.
 * @param viewModel The MainViewModel used for data access.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, viewModel: MainViewModel) {

    // Define mutable state variables for the input fields
    var title by remember { mutableStateOf("") }
    var task1 by remember { mutableStateOf("") }
    var task2 by remember { mutableStateOf("") }
    var task3 by remember { mutableStateOf("") }
    var lockDurationDays by remember { mutableLongStateOf(7) }
    var liked by remember { mutableStateOf(true) }

    // Define scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create a CoroutineScope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        topBar = { BasicTopAppBar(scrollBehavior) },
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false) },
        content = { innerPadding ->
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

                // Display the placeholder image
                HouseworkImage(image = R.drawable.img_placeholder)

                // Display the title input field
                WideTextField(value = title, label = "Title") { value -> title = value }

                // Display the task input fields
                WideTextField(value = task1, label = "Task 1") { value -> task1 = value }
                WideTextField(value = task2, label = "Task 2") { value -> task2 = value }
                WideTextField(value = task3, label = "Task 3") { value -> task3 = value }

                // Display the liked/disliked switch
                LikedDislikedSwitch(liked = liked) { value -> liked = value }

                // Display the locked duration days editor
                LockedDurationDaysEdit(
                    lockDurationDays = lockDurationDays,
                    addOnClick = { lockDurationDays++ },
                    removeOnClick = { lockDurationDays-- }
                )

                // Display a button to upload the housework to Firebase
                WideButton(text = "Create Task", icon = Icons.Outlined.AddCircle, primary = true) {
                    firebaseScope.launch {
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
                    }
                    navController.navigate("list")
                }

                Spacer(
                    modifier = Modifier.height(
                        calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                    )
                )
            }
        }
    )
}