package com.example.abschlussprojekt_husewok.ui.theme.layout.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.OnlyContentScaffold
import com.example.abschlussprojekt_husewok.ui.theme.components.progressIndicator.FullScreenProgressIndicator
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.Constants.auth
import kotlinx.coroutines.delay

/**
 * A Composable function that represents the splash screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for updating the current user.
 */
@Composable
fun SplashScreen(navController: NavController, viewModel: MainViewModel) {
    // State to track if the loading process is ongoing
    var loading by remember { mutableStateOf(false) }

    // State to track the progress of the loading process
    var progress by remember { mutableFloatStateOf(0f) }

    // Launch the effect when the composable is first composed
    LaunchedEffect(Unit) {
        loading = true

        // Simulate a loading process by updating the progress
        for (i in 1..100) {
            progress = i.toFloat() / 100
            delay(5)
        }

        // Check if the user is authenticated
        val user = auth.currentUser
        if (user == null) {
            loading = false

            // Navigate to the login screen if not authenticated
            navController.navigate("login")
        } else {
            // Update the current user
            viewModel.updateCurrentUser(user.uid)
            loading = false

            // Navigate to the home screen if authenticated
            navController.navigate("home")
        }
    }

    // Compose the content of the splash screen
    OnlyContentScaffold {
        if (loading) {
            // Show a full-screen progress indicator during loading
            FullScreenProgressIndicator(progress, Orange80)
        }
    }
}