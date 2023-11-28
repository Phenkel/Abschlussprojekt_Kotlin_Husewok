package com.example.abschlussprojekt_husewok.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.abschlussprojekt_husewok.ui.theme.layout.addTaskScreen.AddTaskScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.feedbackScreen.FeedbackScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.detailScreen.DetailScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.colorGuessScreen.ColorGuessScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.scratchingTicketScreen.ScratchingTicketScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen.HomeScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen.ListScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen.LoginScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.onboardingScreen.OnboardingScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.profileScreen.ProfileScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.splashScreen.SplashScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen.TicTacToeScreen
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel

/**
 * Composable function to set up the navigation host for the app.
 *
 * @param navController The NavHostController used for navigation.
 * @param viewModel The MainViewModel used for data access.
 * @param activity The activity to finish.
 */
@Composable
fun NavigationAppHost(navController: NavHostController, viewModel: MainViewModel, activity: Activity) {
    NavHost(navController = navController, startDestination = "splash") {
        // Splash screen
        composable(
            "splash",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { SplashScreen(navController, viewModel) }

        // Login screen
        composable(
            "login",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            BackHandler(true) {
                activity.finish()
            }
            LoginScreen(navController, viewModel)
        }

        // Home screen
        composable(
            "home",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            BackHandler(true) {
                activity.finish()
            }
            HomeScreen(navController, viewModel)
        }

        // List screen
        composable(
            "list",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ListScreen(navController, viewModel) }

        // Detail screen
        composable(
            route = "detail/{stringValue}",
            arguments = listOf(navArgument("stringValue") { type = NavType.StringType }),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { backStackEntry ->
            val stringValue = backStackEntry.arguments?.getString("stringValue") ?: ""
            DetailScreen(navController, viewModel, stringValue)
        }

        // Add task screen
        composable(
            "addTask",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { AddTaskScreen(navController, viewModel) }

        // Profile screen
        composable(
            "profile",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ProfileScreen(navController, viewModel) }

        // Onboarding screen
        composable(
            "onboarding",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) {
            BackHandler(true) {
                activity.finish()
            }
            OnboardingScreen(navController, viewModel)
        }

        // Tic Tac Toe screen
        composable(
            "tictactoe",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { TicTacToeScreen(navController, viewModel) }

        // Scratching ticket screen
        composable(
            "scratching",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ScratchingTicketScreen(navController, viewModel) }

        // Feedback screen
        composable(
            "feedback",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { FeedbackScreen(navController, viewModel) }

        // Color guess screen
        composable(
            "colorGuess",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ColorGuessScreen(navController, viewModel) }
    }
}