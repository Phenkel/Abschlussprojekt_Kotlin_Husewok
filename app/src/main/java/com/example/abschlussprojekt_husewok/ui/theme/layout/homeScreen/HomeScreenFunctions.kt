package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the home screen.
 */
object HomeScreenFunctions {
    private const val HOMEFUNCTIONS: String = "HomeScreenFunctions"
    /**
     * Displays a success toast with the given title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param context The context used for displaying the toast.
     * @param long Boolean indicating whether the toast should be displayed for a longer duration.
     */
    private fun showSuccessToast(title: String, message: String?, context: Context, long: Boolean) {
        // Display a success toast using MotionToasts library
        MotionToasts.success(
            title = title,
            message = message ?: "",
            activity = context as Activity,
            context = context,
            long = long
        )
    }

    /**
     * Displays an error toast with the given title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param context The context used for displaying the toast.
     */
    private fun showErrorToast(title: String, message: String, context: Context) {
        MotionToasts.error(
            title = title,
            message = message,
            activity = context as Activity,
            context = context
        )
    }

    /**
     * Displays an info toast with the given title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param context The context used for displaying the toast.
     */
    private fun showInfoToast(title: String, message: String, context: Context) {
        MotionToasts.info(
            title = title,
            message = message,
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
     * Suspends the execution of loading housework data.
     * Updates the housework list and retrieves the active housework.
     *
     * @param viewModel The MainViewModel instance to access housework data.
     * @param context The context used for displaying toasts.
     * @param internetScope The CoroutineScope used for running suspend operations.
     */
    suspend fun loadHousework(
        viewModel: MainViewModel,
        navController: NavController,
        context: Context,
        internetScope: CoroutineScope,
    ) {
        // Update the housework list
        val updateHouseworkListSuccess = suspendOperation(internetScope) {
            viewModel.updateHouseworkList()
        }

        if (updateHouseworkListSuccess) {
            // Retrieve the active housework
            val getActiveHouseworkSuccess = suspendOperation(internetScope) {
                viewModel.getActiveHousework()
            }

            if (getActiveHouseworkSuccess) {
                val houseworkList = viewModel.houseworkList.value
                val unlockedHousework = houseworkList.any { !it.isLocked() }
                var housework = viewModel.activeHousework.value

                if (housework?.isLocked() == true) {
                    if (unlockedHousework) {
                        // Navigate to a random game screen if there is unlocked housework
                        val gameScreen = listOf(
                            navController.navigate("tictactoe")
                        )
                        gameScreen.random()
                    } else {
                        // Show success toast when all housework items are locked
                        internetScope.launch {
                            viewModel.updateActiveHousework(true)
                        }
                        showSuccessToast("All Done", "There is no housework left", context, false)
                    }
                } else if (housework?.title == "All done") {
                    if (unlockedHousework) {
                        // Navigate to a random game screen if there is unlocked housework
                        val gameScreen = listOf(
                            navController.navigate("tictactoe")
                        )
                        gameScreen.random()
                    } else {
                        // Show success toast when all housework items are locked
                        internetScope.launch {
                            viewModel.updateActiveHousework(true)
                        }
                        showSuccessToast("All Done", "There is no housework left", context, false)
                    }
                } else if (housework != null) {
                    // Show success toast when active housework is loaded
                    showSuccessToast("Housework loaded", housework.title, context, false)
                } else {
                    Log.w(HOMEFUNCTIONS, "Failed to load housework")

                    // Show error toast when there is an error loading housework data
                    showErrorToast("Loading error", "Please reload the page", context)
                }
            } else {
                Log.w(HOMEFUNCTIONS, "Failed to load housework")

                // Show error toast when there is an error loading active housework
                showErrorToast("Loading error", "Please reload the page", context)
            }
        } else {
            Log.w(HOMEFUNCTIONS, "Failed to load housework list")

            // Show error toast when there is an error updating the housework list
            showErrorToast("Loading error", "Please reload the page", context)
        }
    }

    /**
     * Handles the skip button action for a housework item.
     *
     * @param viewModel The MainViewModel instance to access housework data.
     * @param internetScope The CoroutineScope used for launching the updateActiveHousework coroutine.
     * @param housework The housework item to skip.
     */
    fun skipButton(
        viewModel: MainViewModel,
        navController: NavController,
        internetScope: CoroutineScope,
        housework: Housework?
    ) {
        // Check if the housework item is not already marked as "All done"
        if (housework?.title != "All done") {
            // Navigate to a random game screen if there is unlocked housework
            val gameScreen = listOf(
                navController.navigate("tictactoe")
            )
            gameScreen.random()
        }
    }

    /**
     * Handles the done button action for a housework item.
     *
     * @param viewModel The MainViewModel instance to access housework and API data.
     * @param context The context used for displaying toasts.
     * @param internetScope The CoroutineScope used for launching suspend operations.
     * @param housework The housework item to mark as done.
     * @param user The user associated with the housework.
     */
    suspend fun doneButton(
        viewModel: MainViewModel,
        navController: NavController,
        context: Context,
        internetScope: CoroutineScope,
        housework: Housework,
        user: User?
    ) {
        // Check if the housework item is not already marked as "All done"
        if (housework.title != "All done") {
            // Create a locked version of the housework item with updated lockExpirationDate
            val lockedHousework = housework.copy(lockExpirationDate = housework.updateLockExpirationDate())

            // Update user tasks and skip coins, and upsert the locked housework into Firebase
            val updateUserAndHouseworkSuccess = suspendOperation(internetScope) {
                viewModel.updateUserTasksAndSkipCoins(true)
                viewModel.upsertHouseworkFirebase(lockedHousework)
            }

            if (updateUserAndHouseworkSuccess) {
                val houseworkList = viewModel.houseworkList.value
                val unlockedHousework = houseworkList.any { !it.isLocked() }
                var housework = viewModel.activeHousework.value

                if (unlockedHousework) {
                    // Navigate to a random game screen if there is unlocked housework
                    val gameScreen = listOf(
                        navController.navigate("tictactoe")
                    )
                    gameScreen.random()
                } else {
                    // Show success toast when all housework items are locked
                    internetScope.launch {
                        viewModel.updateActiveHousework(true)
                    }
                    showSuccessToast("All Done", "There is no housework left", context, false)
                }

                // Get the reward for the user
                val getRewardSuccess = suspendOperation(internetScope) {
                    viewModel.getReward()
                }

                if (getRewardSuccess) {
                    // Check the user's reward type
                    if (user?.reward == "Joke") {
                        // Fetch and display a joke reward
                        viewModel.joke.value.let { jokeResult ->
                            when (jokeResult) {
                                is NetworkResult.Success -> {
                                    showSuccessToast(
                                        "${jokeResult.data?.setup}\n${jokeResult.data?.punchline}",
                                        "Joke Reward - ${jokeResult.data?.type}",
                                        context,
                                        true
                                    )
                                }

                                else -> {
                                    Log.w("JokeAPI", "Failed to fetch Joke - ${jokeResult.message.toString()}")
                                }
                            }
                        }
                    } else {
                        // Fetch and display a bored reward
                        viewModel.bored.value.let { boredResult ->
                            when (boredResult) {
                                is NetworkResult.Success -> {
                                    showSuccessToast(
                                        "${boredResult.data?.activity}",
                                        "Bored Reward - ${boredResult.data?.participants}, ${boredResult.data?.type}",
                                        context,
                                        true
                                    )
                                }

                                else -> {
                                    Log.w(HOMEFUNCTIONS, "Failed to fetch Bored - ${boredResult.message.toString()}")
                                }
                            }
                        }
                    }
                } else {
                    Log.w(HOMEFUNCTIONS, "Failed to get reward")
                }
            } else {
                Log.w(HOMEFUNCTIONS, "Failed to update user and housework")
            }
        }
    }

    /**
     * Handles the favorite button action for a housework item.
     *
     * @param viewModel The MainViewModel instance to access housework and API data.
     * @param internetScope The CoroutineScope used for launching suspend operations.
     * @param housework The housework item to toggle the favorite status.
     */
    suspend fun favoriteButton(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        housework: Housework
    ) {
        // Check if the housework item is not already marked as "All done"
        if (housework.title != "All done") {
            // Toggle the favorite status of the housework item
            val upsertHouseworkSuccess = suspendOperation(internetScope) {
                val updatedHousework = housework.copy(isLiked = !housework.isLiked)
                viewModel.upsertHouseworkFirebase(updatedHousework)
            }

            if (upsertHouseworkSuccess) {
                // Update the housework list
                val updateHouseworkListSuccess = suspendOperation(internetScope) {
                    viewModel.updateHouseworkList()
                }

                if (updateHouseworkListSuccess) {
                    // Fetch the active housework
                    viewModel.getActiveHousework()
                } else {
                    Log.w(HOMEFUNCTIONS, "Failed to update housework list")
                }
            } else {
                Log.w(HOMEFUNCTIONS, "Failed to upsert housework")
            }
        }
    }

    /**
     * Handles the info button action for a housework item.
     *
     * @param context The context used for displaying the info toast.
     * @param housework The housework item to show the info for.
     */
    fun infoButton(context: Context, housework: Housework) {
        // Show an info toast with the tasks and title of the housework
        showInfoToast(
            "${housework.task1}\n${housework.task2}\n${housework.task3}",
            housework.title,
            context
        )
    }
}