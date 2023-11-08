package com.example.abschlussprojekt_husewok.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.abschlussprojekt_husewok.ui.theme.layout.AddTaskScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.DetailScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.HomeScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.ListScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.LoginScreen
import com.example.abschlussprojekt_husewok.ui.theme.layout.ProfileScreen
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel

@Composable
fun NavigationAppHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, viewModel) }
        composable("home") { HomeScreen(navController, viewModel) }
        composable("list") { ListScreen(navController, viewModel) }
        composable("detail") { DetailScreen(navController, viewModel) }
        composable("addTask") { AddTaskScreen(navController, viewModel) }
        composable("profile") { ProfileScreen(navController, viewModel) }
    }
}