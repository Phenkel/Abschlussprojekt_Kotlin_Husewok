package com.example.abschlussprojekt_husewok.ui.theme.layout.feedbackScreen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function that displays the feedback screen.
 *
 * @param navController The navigation controller for navigating between screens.
 * @param viewModel The view model for accessing and updating data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navController: NavController, viewModel: MainViewModel) {
    // State variables for the title and description input fields
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Scroll state for the feedback screen
    val scrollState = rememberScrollState()

    // Scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Get the current context
    val context = LocalContext.current

    // Coroutine scope for internet operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Compose the feedback screen UI
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "home") },
        bottomBar = { AnimatedBottomAppBar(navController, 3, false, false, false, true) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                // Input field for the feedback title
                WideTextField(
                    value = title,
                    label = "Title",
                    onValueChange = { value -> title = value }
                )

                // Input field for the feedback description
                WideTextField(
                    value = description,
                    label = "Description",
                    onValueChange = { value -> description = value }
                )

                // Button to send the feedback
                WideButton(
                    text = "Send feedback",
                    icon = Icons.Outlined.Email,
                    primary = true
                ) {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        // Launch a coroutine in the IO context to submit the feedback
                        internetScope.launch {
                            viewModel.addFeedback(title, description)
                            title = ""
                            description = ""
                        }

                        // Show a success toast message
                        MotionToasts.success(
                            title = "Thanks for sending a feedback",
                            message = "It means much to me",
                            activity = context as Activity,
                            context = context
                        )
                    } else {
                        // Show an error toast message if any field is empty
                        MotionToasts.error(
                            title = "Please fill out every field",
                            message = "Could not send feedback",
                            activity = context as Activity,
                            context = context
                        )
                    }
                }

                // Scrollable box for displaying additional information
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "Feedback and Improvement Center\n" +
                                "\n" +
                                "I'd love to hear from you! Your feedback helps me make Hûséwøk better. Whether you want to report a bug, suggest a new feature, or share your thoughts, this is the place to do it.\n" +
                                "\n" +
                                "How Can You Help?\n" +
                                "\n" +
                                "Report Bugs: Encountered a glitch? Let me know the details so I can squash it.\n" +
                                "\n" +
                                "Suggest Features: Have an idea to make Hûséwøk even more awesome? I'm all ears.\n" +
                                "\n" +
                                "General Feedback: Share your thoughts, positive or constructive. Your insights matter!\n" +
                                "\n" +
                                "Your Feedback Matters:\n" +
                                "\n" +
                                "I appreciate the time you take to help me improve. Every suggestion and bug report makes Hûséwøk a better experience for everyone.\n" +
                                "\n" +
                                "Thank You for Being Part of Hûséwøk!",
                        color = Color.White,
                        fontSize = calcSp(percentage = 0.03f)
                    )
                }
            }
        }
    }
}