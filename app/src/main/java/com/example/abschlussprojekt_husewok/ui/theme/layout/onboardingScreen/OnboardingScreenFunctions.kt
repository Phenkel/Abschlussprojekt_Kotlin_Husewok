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
     * Executes a suspend function within a CoroutineScope and returns a boolean indicating the success of the operation.
     *
     * @param internetScope The CoroutineScope used for the suspend operation.
     * @param operation The suspend function to be executed.
     * @return Returns true if the operation completes successfully, false otherwise.
     */
    private suspend fun suspendOperation(
        internetScope: CoroutineScope,
        operation: suspend CoroutineScope.() -> Unit
    ): Boolean {
        return suspendCoroutine { continuation ->
            // Launch a coroutine within the internetScope
            internetScope.launch {
                try {
                    // Execute the suspend operation
                    operation()

                    // Resume the continuation with a value of true indicating successful completion
                    continuation.resume(true)
                } catch (e: Exception) {
                    // Resume the continuation with a value of false indicating unsuccessful completion
                    continuation.resume(false)
                }
            }
        }
    }

    /**
     * Suspended function that loads data from the server and performs navigation based on the result.
     *
     * @param viewModel The view model for accessing and updating data.
     * @param navController The navigation controller for navigating between screens.
     * @param internetScope The coroutine scope for internet operations.
     */
    suspend fun loadData(viewModel: MainViewModel, navController: NavController, internetScope: CoroutineScope) {
        // Update the housework list and check if it was successful
        val updateHouseworkListSuccess = suspendOperation(internetScope) {
            viewModel.updateHouseworkList()
        }

        if (updateHouseworkListSuccess) {
            // Get active housework and check if it was successful
            val getActiveHouseworkSuccess = suspendOperation(internetScope) {
                viewModel.getActiveHousework()
            }

            if (getActiveHouseworkSuccess) {
                // Navigate to the "home" screen if active housework was successfully retrieved
                navController.navigate("home")
            } else {
                Log.w(ONBOARDINGFUNCTIONS, "Failed to get active housework")
            }
        } else {
            Log.w(ONBOARDINGFUNCTIONS, "Failed to update housework list")
        }
    }
}