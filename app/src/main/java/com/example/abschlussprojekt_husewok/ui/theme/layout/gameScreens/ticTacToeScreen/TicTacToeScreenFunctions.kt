package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen

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
object TicTacToeScreenFunctions {
    private const val TICTACTOEFUNCTIONS: String = "TicTacToeScreenFunctions"
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
     * Checks if the game is over by analyzing the current state of the board.
     * @param moves The list representing the state of the board.
     * @return Boolean? Returns true if the player has won, false if the AI has won, or null if the game is not over yet.
     */
    fun gameOverCheck(moves: List<Boolean?>): Boolean? {
        val winningCombos = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        for (combo in winningCombos) {
            val values = combo.map { moves[it] }
            if (values == listOf(true, true, true)) {
                return true // Player has won
            }
            if (values == listOf(false, false, false)) {
                return false // AI has won
            }
        }

        if (!moves.contains(null)) {
            return false // Game is a draw
        }

        return null // Game is not over yet
    }

    /**
     * Finds a winning or blocking move for the AI.
     * @param freeIndices The list of available indices to make a move.
     * @param moves The mutable list representing the state of the board.
     * @param playerMove Boolean value indicating whether the move is for the player or the AI.
     * @return Int? Returns the index of the winning or blocking move, or null if no such move is found.
     */
    fun findAiMove(freeIndices: List<Int>, moves: MutableList<Boolean?>, playerMove: Boolean): Int? {
        for (move in freeIndices) {
            // Temporarily make the move to check if it leads to a win
            moves[move] = playerMove

            // Check if the move leads to a win
            val gameWon = gameOverCheck(moves)
            if (gameWon != null && gameWon == playerMove) {
                // Undo the temporary move
                moves[move] = null
                return move
            }

            // Undo the temporary move
            moves[move] = null
        }

        // No winning or blocking move found
        return null
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
            Log.w(TICTACTOEFUNCTIONS, "Failed to update housework", exception)

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