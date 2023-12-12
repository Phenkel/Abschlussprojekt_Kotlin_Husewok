package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.colorGuessScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.scratchingTicketScreen.ScratchingTicketScreenFunctions
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A helper object containing functions related to the color guess screen.
 */
object ColorGuessScreenFunctions {
    private const val COLORGUESSFUNCTIONS: String = "ColorGuessScreenFunctions"

    /**
     * Shows a success toast using MotionToasts library.
     *
     * @param title The title of the toast.
     * @param message The optional message to be displayed in the toast. If null, an empty string will be used.
     * @param context The Context in which the toast is shown. Must be an instance of Activity.
     * @param mainScope The CoroutineScope used for launching the toast operation.
     */
    private fun showSuccessToast(
        title: String,
        message: String?,
        context: Context,
        mainScope: CoroutineScope
    ) {
        mainScope.launch {
            MotionToasts.success(
                title = title,
                message = message ?: "",
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Retrieves new housework and updates the games in the user profile.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController used for navigation.
     * @param mainScope The CoroutineScope used for launching coroutines.
     * @param context The Context in which the operation is performed.
     * @param gameWon A boolean indicating whether the game was won or lost.
     */
    fun getNewHousework(
        viewModel: MainViewModel,
        navController: NavController,
        mainScope: CoroutineScope,
        context: Context,
        gameWon: Boolean
    ) {
        viewModel.updateActiveHousework(gameWon).addOnSuccessListener {
            viewModel.updateUserGames(gameWon)
            mainScope.launch {
                navController.navigate("home")
            }
            showSuccessToast(
                title = viewModel.activeHousework.value?.title.toString(),
                message = if (gameWon) "Game won" else "Game lost",
                context,
                mainScope
            )
        }.addOnFailureListener { exception ->
            Log.w(COLORGUESSFUNCTIONS, "Failed to update housework", exception)
            getNewHousework(
                viewModel,
                navController,
                mainScope,
                context,
                gameWon
            )
        }
    }
}