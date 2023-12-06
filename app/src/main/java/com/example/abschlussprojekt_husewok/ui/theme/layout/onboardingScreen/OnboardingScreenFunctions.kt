package com.example.abschlussprojekt_husewok.ui.theme.layout.onboardingScreen

import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the onboarding screen.
 */
object OnboardingScreenFunctions {
    private const val ONBOARDINGFUNCTIONS: String = "OnboardingScreenFunctions"

    /**
     * Loads the necessary data for the app by updating the housework list and fetching the active housework.
     * Once the data is loaded, navigates to the home screen.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param navController The NavController used for navigation.
     */
    fun loadData(viewModel: MainViewModel, navController: NavController) {
        // Update the housework list
        viewModel.updateHouseworkList().addOnSuccessListener {
            // Fetch the active housework
            viewModel.getActiveHousework().addOnSuccessListener {
                // Navigate to the home screen
                navController.navigate("home")
            }.addOnFailureListener { exception ->
                // Log the failure to fetch the active housework
                Log.w(ONBOARDINGFUNCTIONS, "Failed to get active housework", exception)
            }
        }.addOnFailureListener { exception ->
            // Log the failure to update the housework list
            Log.w(ONBOARDINGFUNCTIONS, "Failed to update housework list", exception)
        }
    }
}