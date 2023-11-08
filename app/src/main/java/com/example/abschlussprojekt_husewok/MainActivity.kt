package com.example.abschlussprojekt_husewok

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.abschlussprojekt_husewok.ui.NavigationAppHost
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it, "MainViewModel", MainViewModelFactory(
                        LocalContext.current.applicationContext as Application
                    )
                )
                val navController = rememberNavController()
                NavigationAppHost(navController, viewModel)
            }
        }
    }
}