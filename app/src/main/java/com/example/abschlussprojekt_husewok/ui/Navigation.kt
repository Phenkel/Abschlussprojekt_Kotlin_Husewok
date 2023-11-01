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

@Composable
fun NavigationAppHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Home") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("list") { ListScreen(navController) }
        composable("detail") { DetailScreen(navController) }
        composable("addTask") { AddTaskScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}