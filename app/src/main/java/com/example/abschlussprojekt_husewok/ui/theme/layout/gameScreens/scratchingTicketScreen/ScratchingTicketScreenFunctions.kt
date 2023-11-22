package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.scratchingTicketScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the Tic-Tac-Toe screen.
 */
object ScratchingTicketScreenFunctions {
    private const val SCRATCHINGTICKETFUNCTIONS: String = "TicTacToeScreenFunctions"
    /**
     * Displays a success toast with the given title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param context The context used for displaying the toast.
     */
    private fun showSuccessToast(title: String, message: String?, context: Context) {
        // Display a success toast using MotionToasts library
        MotionToasts.success(
            title = title,
            message = message ?: "",
            activity = context as Activity,
            context = context
        )
    }

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
     * Asynchronously retrieves new housework and updates the active housework in the ViewModel.
     * Shows a success toast if the update is successful, otherwise retries the operation.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController instance.
     * @param internetScope The CoroutineScope for internet operations.
     * @param context The Context instance.
     * @param gameWon A boolean indicating whether the game was won or not.
     */
    suspend fun getNewHousework(
        viewModel: MainViewModel,
        navController: NavController,
        internetScope: CoroutineScope,
        context: Context,
        gameWon: Boolean
    ) {
        // Perform the updateActiveHousework operation asynchronously
        val updateHouseworkSuccess = suspendOperation(internetScope) {
            viewModel.updateActiveHousework(gameWon)
        }

        if (updateHouseworkSuccess) {
            // Show a success toast with the updated housework title
            showSuccessToast(
                title = viewModel.activeHousework.value?.title.toString(),
                message = "New housework",
                context
            )

            // Navigates to home screen
            navController.navigate("home")
        } else {
            Log.w(SCRATCHINGTICKETFUNCTIONS, "Failed to update housework")

            // Retry the operation if the update was not successful
            getNewHousework(
                viewModel,
                navController,
                internetScope,
                context,
                gameWon
            )
        }
    }
}