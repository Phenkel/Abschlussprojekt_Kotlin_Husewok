package com.example.abschlussprojekt_husewok.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.abschlussprojekt_husewok.ui.theme.layout.addTaskScreen.AddTaskScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.detailScreen.DetailScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen.HomeScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen.ListScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen.LoginScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.onboardingScreen.OnboardingScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.profileScreen.ProfileScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.splashScreen.SplashScreen
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
        composable("detail") { DetailScreen(navController, viewModel) }
        composable("addTask") { AddTaskScreen(navController, viewModel) }
        composable("profile") { ProfileScreen(navController, viewModel) }
        composable("onboarding") { OnboardingScreen(navController, viewModel) }
    }
}