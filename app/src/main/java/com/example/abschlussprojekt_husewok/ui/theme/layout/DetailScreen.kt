package com.example.abschlussprojekt_husewok.ui.theme.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.esatgozcu.rollingnumber.RollingNumberVM
import com.esatgozcu.rollingnumber.RollingNumberView
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.components.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.components.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.popovanton0.heartswitch.HeartSwitch
import com.popovanton0.heartswitch.HeartSwitchColors

@Preview
@Composable
fun previewDetailScreen() {
    DetailScreen(navController = rememberNavController())
}

/**
 * Composable function to display the detail screen.
 *
 * TODO: ViewModel
 * TODO: Add updateTask Button onClick
 * TODO: Composable for editable information
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController) {
    // Create a state variable for the chosen housework
    val housework by remember {
        mutableStateOf(
            Housework(
                image = R.drawable.img_organize_paperwork,
                title = "Organize Paperwork",
                task1 = "Sort and organize documents",
                task2 = "File important papers",
                task3 = "Shred or discard unnecessary documents",
                lockDurationDays = 1
            )
        )
    }
    // Create a state variable for the housework title
    var title by remember {
        mutableStateOf(housework.title)
    }

    // Create a state variable for the 1. housework task
    var task1 by remember {
        mutableStateOf(housework.task1)
    }

    // Create a state variable for the 2. housework task
    var task2 by remember {
        mutableStateOf(housework.task2)
    }

    // Create a state variable for the 3. housework task
    var task3 by remember {
        mutableStateOf(housework.task3)
    }

    // Create a state variable for the housework lock duration days
    var lockDurationDays by remember {
        mutableLongStateOf(housework.lockDurationDays)
    }

    // Create a state variable for the housework liked status
    var liked by remember {
        mutableStateOf(housework.isLiked)
    }

    // Create a state variable for the liked information
    var isLikedText by remember {
        mutableStateOf(
            if (liked) "Liked" else "Disliked"
        )
    }

    // Create a nested scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create a snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    // Create a coroutine scope
    val scope = rememberCoroutineScope()

    // Define the layout of the screen
    Scaffold(containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),

        // Display a topAppBar
        topBar = { BasicTopAppBar(scrollBehavior) },

        // Display a bottomAppBar
        bottomBar = { AnimatedBottomAppBar(navController, 1, false, true, false) },

        // Display a snackbar
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Purple40,
                    contentColor = Orange80,
                    shape = ShapeDefaults.ExtraSmall
                )
            }
        },

        content = { innerPadding ->

            // Use a column to show details of the chosen housework
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    calcDp(
                        percentage = 0.02f, dimension = Dimension.Height
                    )
                ),
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .verticalScroll(state = rememberScrollState())
            ) {

                Spacer(
                    modifier = Modifier.height(
                        calcDp(
                            percentage = 0.02f, dimension = Dimension.Height
                        )
                    )
                )

                // Display a image of the chosen housework
                Image(
                    painter = painterResource(id = housework.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(
                            calcDp(
                                percentage = 0.8f, dimension = Dimension.Width
                            )
                        )
                        .height(calcDp(percentage = 0.333f, dimension = Dimension.Height))
                        .border(
                            width = calcDp(percentage = 0.8f, dimension = Dimension.Width) / 100,
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Orange80, Color.White, Purple80
                                )
                            ),
                            shape = ShapeDefaults.ExtraSmall
                        )
                )

                // Use a column to display all editable settings of the chosen housework
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        calcDp(
                            percentage = 0.02f, dimension = Dimension.Height
                        )
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {

                    // Display a text field to edit the title
                    OutlinedTextField(value = title,
                        onValueChange = { value ->
                            title = value
                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Purple40,
                            unfocusedBorderColor = Orange40,
                            focusedLabelColor = Purple80,
                            unfocusedLabelColor = Orange40,
                            textColor = Color.White
                        ),
                        label = { Text(text = "Title") })

                    // Display a text field to edit the 1. task
                    OutlinedTextField(value = task1,
                        onValueChange = { value ->
                            task1 = value
                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Purple40,
                            unfocusedBorderColor = Orange40,
                            focusedLabelColor = Purple80,
                            unfocusedLabelColor = Orange40,
                            textColor = Color.White
                        ),
                        label = { Text(text = "1. Task") })

                    // Display a text field to edit the 2. task
                    OutlinedTextField(value = task2,
                        onValueChange = { value ->
                            task2 = value
                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Purple40,
                            unfocusedBorderColor = Orange40,
                            focusedLabelColor = Purple80,
                            unfocusedLabelColor = Orange40,
                            textColor = Color.White
                        ),
                        label = { Text(text = "2. Task") })

                    // Display a text field to edit the 3. task
                    OutlinedTextField(value = task3,
                        onValueChange = { value ->
                            task3 = value
                        },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Purple40,
                            unfocusedBorderColor = Orange40,
                            focusedLabelColor = Purple80,
                            unfocusedLabelColor = Orange40,
                            textColor = Color.White
                        ),
                        label = { Text(text = "3. Task") })

                    // Display a row to edit the liked stats
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = isLikedText,
                            color = Color.White,
                            fontSize = calcSp(percentage = 0.05f)
                        )

                        // Display a heart switch to edit the liked status
                        HeartSwitch(
                            checked = liked,
                            onCheckedChange = { value ->
                                liked = value
                                isLikedText = if (liked) {
                                    "Liked"
                                } else {
                                    "Disliked"
                                }
                            },
                            colors = HeartSwitchColors(
                                checkedTrackColor = Purple40,
                                uncheckedTrackColor = Orange40,
                                checkedTrackBorderColor = Purple80,
                                uncheckedTrackBorderColor = Orange80,
                                checkedThumbColor = Purple80,
                                uncheckedThumbColor = Orange80
                            )
                        )
                    }

                    // Display a row to edit the locked duration days
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = "Locked Days",
                            color = Color.White,
                            fontSize = calcSp(percentage = 0.05f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Display a icon button to decrease the locked duration days
                            IconButton(
                                onClick = { lockDurationDays-- },
                                enabled = lockDurationDays > 1
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_remove_circle),
                                    contentDescription = null,
                                    tint = Orange80
                                )
                            }

                            // Display a rolling number view to show the locked duration days
                            RollingNumberView(
                                vm = RollingNumberVM(
                                    number = String.format("%02d", lockDurationDays),
                                    textStyle = TextStyle(
                                        fontSize = calcSp(percentage = 0.05f), color = Color.White
                                    )
                                )
                            )

                            // Display a icon button to increase the locked duration days
                            IconButton(
                                onClick = { lockDurationDays++ },
                                enabled = lockDurationDays < 14
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = null,
                                    tint = Purple80
                                )
                            }
                        }
                    }
                }

                // Display a row to show the locked status
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = if (housework.isLocked()) "Locked:" else "Unlocked:",
                        color = Color.White,
                        fontSize = calcSp(percentage = 0.05f)
                    )
                    (if (housework.lockExpirationDate != null) housework.lockExpirationDate else "")?.let { expirationDate ->
                        Text(
                            text = expirationDate,
                            color = Color.White,
                            fontSize = calcSp(percentage = 0.05f)
                        )
                    }
                }

                // Display a button to save the edits
                Button(shape = ShapeDefaults.ExtraSmall, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40, contentColor = Orange80
                ), modifier = Modifier
                    .width(
                        calcDp(
                            percentage = 0.8f, dimension = Dimension.Width
                        )
                    )
                    .height(
                        calcDp(
                            percentage = 0.05f, dimension = Dimension.Height
                        )
                    ), onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh, contentDescription = null
                    )
                    Text(
                        text = "Update task"
                    )
                }

                // Display a button to delete the task
                Button(shape = ShapeDefaults.ExtraSmall, colors = ButtonDefaults.buttonColors(
                    containerColor = Orange80, contentColor = Purple40
                ), modifier = Modifier
                    .width(
                        calcDp(
                            percentage = 0.8f, dimension = Dimension.Width
                        )
                    )
                    .height(
                        calcDp(
                            percentage = 0.05f, dimension = Dimension.Height
                        )
                    ), onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete, contentDescription = null
                    )
                    Text(
                        text = "Delete task"
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        calcDp(
                            percentage = 0.02f, dimension = Dimension.Height
                        )
                    )
                )
            }
        })
}