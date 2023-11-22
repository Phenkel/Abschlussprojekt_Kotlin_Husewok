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
                message = if (gameWon) "Game won" else "Game lost",
                context
            )

            // Navigates to home screen
            navController.navigate("home")
        } else {
            Log.w(TICTACTOEFUNCTIONS, "Failed to update housework")

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