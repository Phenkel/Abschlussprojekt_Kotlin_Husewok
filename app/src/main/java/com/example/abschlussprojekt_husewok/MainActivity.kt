package com.example.abschlussprojekt_husewok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import com.example.abschlussprojekt_husewok.data.repository.Repository
import com.example.abschlussprojekt_husewok.ui.NavigationAppHost
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModelFactory

/**
 * The main activity of the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Retrieve the ViewModelStoreOwner from the LocalViewModelStoreOwner
            val owner = LocalViewModelStoreOwner.current
            owner?.let {
                // Create an instance of MainViewModel using the ViewModelProvider
                val viewModel: MainViewModel = viewModel(
                    it, "MainViewModel", MainViewModelFactory(
                        Repository(BoredApi, JokeApi)
                    )
                )

                // Create the NavController for navigation
                val navController = rememberNavController()

                // Set up the navigation host with the NavController and MainViewModel
                NavigationAppHost(navController, viewModel, this)
            }
        }
    }
}