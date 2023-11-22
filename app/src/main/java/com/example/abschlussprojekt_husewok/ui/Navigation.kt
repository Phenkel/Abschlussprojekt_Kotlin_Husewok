package com.example.abschlussprojekt_husewok.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.abschlussprojekt_husewok.ui.theme.layout.addTaskScreen.AddTaskScreen
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
        composable("splash") { SplashScreen(navController, viewModel) }
        composable("login") { LoginScreen(navController, viewModel) }
        composable("home") { HomeScreen(navController, viewModel) }
        composable("list") { ListScreen(navController, viewModel) }
        composable(
            route = "detail/{stringValue}",
            arguments = listOf(navArgument("stringValue") { type = NavType.StringType })
        ) { backStackEntry ->
            val stringValue = backStackEntry.arguments?.getString("stringValue") ?: ""
            DetailScreen(navController, viewModel, stringValue)
        }
        composable("addTask") { AddTaskScreen(navController, viewModel) }
        composable("profile") { ProfileScreen(navController, viewModel) }
        composable("onboarding") { OnboardingScreen(navController, viewModel) }
        composable("tictactoe") { TicTacToeScreen(navController, viewModel) }
        composable("scratching") { ScratchingTicketScreen(navController, viewModel) }
    }
}