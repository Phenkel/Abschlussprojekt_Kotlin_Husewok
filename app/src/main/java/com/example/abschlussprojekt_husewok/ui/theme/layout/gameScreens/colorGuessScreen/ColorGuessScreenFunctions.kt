package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.colorGuessScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen.TicTacToeScreenFunctions
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the color guess screen.
 */
object ColorGuessScreenFunctions {
    private const val COLORGUESSFUNCTIONS: String = "ColorGuessScreenFunctions"
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
     * Retrieves a new housework item based on the outcome of the game and updates the active housework in the ViewModel.
     * Shows a success toast message indicating the result of the game.
     * If there is a failure in updating the housework, recursively calls itself to retry.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param navController The NavController used for navigating.
     * @param context The context used for displaying toast messages.
     * @param gameWon Indicates whether the game was won or lost.
     */
    fun getNewHousework(
        viewModel: MainViewModel,
        navController: NavController,
        context: Context,
        gameWon: Boolean
    ) {
        viewModel.updateActiveHousework(gameWon).addOnSuccessListener {
            // Show a success toast message with the title of the active housework and the result of the game
            showSuccessToast(
                title = viewModel.activeHousework.value?.title.toString(),
                message = if (gameWon) "Game won" else "Game lost",
                context
            )
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in updating the housework
            Log.w(COLORGUESSFUNCTIONS, "Failed to update housework", exception)

            // Retry getting new housework by recursively calling the function
            getNewHousework(
                viewModel,
                navController,
                context,
                gameWon
            )
        }
    }
}