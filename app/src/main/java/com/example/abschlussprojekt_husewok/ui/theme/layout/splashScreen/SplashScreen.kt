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
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.OnlyContentScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.progressIndicator.FullScreenProgressIndicator
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.Constants.auth
import kotlinx.coroutines.delay

/**
 * Composable function for the splash screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@Composable
fun SplashScreen(navController: NavController, viewModel: MainViewModel) {
    // Define mutable state variables for loading and progress
    var loading by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    // Execute the code inside LaunchedEffect when the composable is launched
    LaunchedEffect(Unit) {
        loading = true

        // Simulate a loading progress from 1 to 100
        for (i in 1..100) {
            progress = i.toFloat() / 100
            delay(5)
        }

        // Check if a user is already logged in
        val user = auth.currentUser
        if (user == null) {
            loading = false
            // Navigate to the login screen
            navController.navigate("login")
        } else {
            // Update the current user in the view model
            viewModel.updateCurrentUser(user.uid)
            loading = false
            // Navigate to the home screen
            navController.navigate("home")
        }
    }

    // Create the splash screen layout using OnlyContentScaffold
    OnlyContentScaffold {
        if (loading) {
            // Display a full-screen progress indicator with the current progress
            FullScreenProgressIndicator(progress, Orange80)
        }
    }
}