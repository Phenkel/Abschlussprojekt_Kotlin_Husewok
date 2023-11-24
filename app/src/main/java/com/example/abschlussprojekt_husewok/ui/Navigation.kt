package com.example.abschlussprojekt_husewok.ui

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
 */
@Composable
fun NavigationAppHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "splash") {
        composable(
            "splash",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { SplashScreen(navController, viewModel) }
        composable(
            "login",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { LoginScreen(navController, viewModel) }
        composable(
            "home",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { HomeScreen(navController, viewModel) }
        composable(
            "list",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ListScreen(navController, viewModel) }
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
        composable(
            "addTask",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { AddTaskScreen(navController, viewModel) }
        composable(
            "profile",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ProfileScreen(navController, viewModel) }
        composable(
            "onboarding",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { OnboardingScreen(navController, viewModel) }
        composable(
            "tictactoe",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { TicTacToeScreen(navController, viewModel) }
        composable(
            "scratching",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { ScratchingTicketScreen(navController, viewModel) }
        composable(
            "feedback",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }
        ) { FeedbackScreen(navController, viewModel) }
    }
}