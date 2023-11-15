package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A helper object containing functions related to the home screen.
 */
object HomeScreenFunctions {
    /**
     * Refreshes the data and handles the logic for updating the active housework and displaying toast messages.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param context The current context.
     * @param firebaseScope The CoroutineScope used for launching coroutines.
     * @param housework The active housework item.
     * @param houseworkList The list of housework items.
     */
    suspend fun refresh(
        viewModel: MainViewModel,
        context: Context,
        firebaseScope: CoroutineScope,
        housework: Housework?,
        houseworkList: List<Housework>
    ) {
        // Refresh the housework list and get the active housework in a non-cancellable context
        withContext(NonCancellable) {
            viewModel.updateHouseworkList()
            viewModel.getActiveHousework()
        }

        if (housework?.isLocked() == true) {
            var unlockedHousework = false

            // Check if there is any unlocked housework
            houseworkList.forEach { hw ->
                if (!hw.isLocked()) {
                    unlockedHousework = true
                }
            }

            if (unlockedHousework) {
                // Update the active housework asynchronously
                firebaseScope.launch {
                    viewModel.updateActiveHousework(true) // TODO: Remove this line
                }

                // TODO: Navigate to the game screen
            } else {
                // Update the active housework asynchronously
                firebaseScope.launch {
                    viewModel.updateActiveHousework(true)
                }
                // Show an "All Done" toast message
                MotionToasts.info(
                    title = "All Done",
                    message = "There is no housework left",
                    activity = context as Activity,
                    context = context
                )
            }
        } else if (housework?.title == "All done") {
            var unlockedHousework = false

            // Check if there is any unlocked housework
            houseworkList.forEach { hw ->
                if (!hw.isLocked()) {
                    unlockedHousework = true
                }
            }

            if (unlockedHousework) {
                // Update the active housework asynchronously
                firebaseScope.launch {
                    viewModel.updateActiveHousework(true) // TODO: Remove this line
                }

                // TODO: Navigate to the game screen
            } else {
                // Show an "All Done" toast message
                MotionToasts.info(
                    title = "All Done",
                    message = "There is no housework left",
                    activity = context as Activity,
                    context = context
                )
            }
        } else if (housework != null) {
            // Show a success toast message if active housework is found
            MotionToasts.success(
                title = "Success",
                message = "Active housework found",
                activity = context as Activity,
                context = context
            )
        } else {
            // Show an error toast message if no active housework is found
            MotionToasts.error(
                title = "Error",
                message = "No active housework found. Please reload again",
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Initializes the activity and handles the logic for starting the game or displaying a toast message.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param context The current context.
     * @param firebaseScope The CoroutineScope used for launching coroutines.
     * @param houseworkList The list of housework items.
     */
    fun onCreate(
        viewModel: MainViewModel,
        context: Context,
        firebaseScope: CoroutineScope,
        houseworkList: List<Housework>
    ) {
        var unlockedHousework = false

        // Check if there is any unlocked housework
        houseworkList.forEach { hw ->
            if (!hw.isLocked()) {
                unlockedHousework = true
            }
        }

        if (unlockedHousework) {
            // Update the active housework asynchronously
            firebaseScope.launch {
                viewModel.updateActiveHousework(true) // TODO: Remove this line
            }

            // TODO: Navigate to the game screen
        } else {
            // Update the active housework asynchronously
            firebaseScope.launch {
                viewModel.updateActiveHousework(true)
            }
            // Show an "All Done" toast message
            MotionToasts.info(
                title = "All Done",
                message = "There is no housework left",
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Checks if all housework is done and performs the necessary actions.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param context The current context.
     * @param firebaseScope The CoroutineScope used for launching coroutines.
     * @param houseworkList The list of housework items.
     */
    fun allDoneFound(
        viewModel: MainViewModel,
        context: Context,
        firebaseScope: CoroutineScope,
        houseworkList: List<Housework>
    ) {
        var unlockedHousework = false

        // Check if there is any unlocked housework
        houseworkList.forEach { hw ->
            if (!hw.isLocked()) {
                unlockedHousework = true
            }
        }

        if (unlockedHousework) {
            firebaseScope.launch {
                viewModel.updateActiveHousework(true) // TODO: Remove this line
            }

            // TODO: Navigate to the game screen
        } else {
            // Show an "All Done" toast message
            MotionToasts.info(
                title = "All Done",
                message = "There is no housework left",
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Performs the necessary actions when the skip button is pressed.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param firebaseScope The CoroutineScope used for launching coroutines.
     * @param housework The current housework item.
     */
    fun skipButton(
        viewModel: MainViewModel,
        firebaseScope: CoroutineScope,
        housework: Housework?
    ) {
        // Check if the housework title is not "All done"
        if (housework?.title != "All done") {
            firebaseScope.launch {
                // Update the active housework status
                viewModel.updateActiveHousework(true)

                // Update the user tasks and skip coins
                viewModel.updateUserTasksAndSkipCoins(false)
            }
        }
    }

    /**
     * Performs the necessary actions when the done button is pressed.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param context The context of the application.
     * @param firebaseScope The CoroutineScope used for launching coroutines related to Firebase operations.
     * @param apiScope The CoroutineScope used for launching coroutines related to API operations.
     * @param mainScope The CoroutineScope used for launching coroutines related to UI operations.
     * @param housework The current housework item.
     * @param user The current user.
     * @param bored The result of the bored API call.
     * @param joke The result of the joke API call.
     */
    fun doneButton(
        viewModel: MainViewModel,
        context: Context,
        firebaseScope: CoroutineScope,
        apiScope: CoroutineScope,
        mainScope: CoroutineScope,
        housework: Housework,
        user: User?,
        bored: NetworkResult<Bored>,
        joke: NetworkResult<Joke>
    ) {
        // Check if the housework title is not "All done"
        if (housework.title != "All done") {
            // Create a locked housework object based on the current housework
            val lockedHousework = Housework(
                image = housework.image,
                title = housework.title,
                task1 = housework.task1,
                task2 = housework.task2,
                task3 = housework.task3,
                isLiked = housework.isLiked,
                lockDurationDays = housework.lockDurationDays,
                lockExpirationDate = housework.updateLockExpirationDate(),
                default = housework.default,
                id = housework.id
            )

            // Update user tasks and skip coins
            firebaseScope.launch {
                viewModel.updateUserTasksAndSkipCoins(true)
                viewModel.upsertHouseworkFirebase(lockedHousework)
                viewModel.updateHouseworkList()
                viewModel.updateActiveHousework(true) // TODO: Remove
                // TODO: Navigate to Game screen
            }

            // Get the reward based on the user's reward type
            apiScope.launch {
                viewModel.getReward()
                if (user?.reward == "Joke") {
                    withContext(NonCancellable) {
                        while (joke is NetworkResult.Loading) {
                            delay(100)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        when (joke) {
                            is NetworkResult.Success -> {
                                mainScope.launch {
                                    MotionToasts.success(
                                        title = "${joke.data?.setup}\n${joke.data?.punchline}",
                                        message = "Joke Reward - ${joke.data?.type}",
                                        activity = context as Activity,
                                        context = context,
                                        long = true
                                    )
                                }
                            }
                            else -> {
                                Log.w("JokeUI", "Failed to fetch joke")
                            }
                        }
                    }
                } else {
                    withContext(NonCancellable) {
                        while (bored is NetworkResult.Loading) {
                            delay(100)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        when (bored) {
                            is NetworkResult.Success -> {
                                mainScope.launch {
                                    MotionToasts.success(
                                        title = "${bored.data?.activity}",
                                        message = "Bored Reward - ${bored.data?.participants}, ${bored.data?.type}",
                                        activity = context as Activity,
                                        context = context,
                                        long = true
                                    )
                                }
                            }
                            else -> {
                                Log.w("BoredUI", "Failed to fetch bored")
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Performs the necessary actions when the icon button is pressed.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param firebaseScope The CoroutineScope used for launching coroutines related to Firebase operations.
     * @param housework The housework item associated with the button.
     */
    fun iconButton(
        viewModel: MainViewModel,
        firebaseScope: CoroutineScope,
        housework: Housework
    ) {
        // Check if the housework title is not "All done"
        if (housework.title != "All done") {
            firebaseScope.launch {
                // Toggle the isLiked property of the housework item and update it in Firebase
                viewModel.upsertHouseworkFirebase(
                    Housework(
                        image = housework.image,
                        title = housework.title,
                        task1 = housework.task1,
                        task2 = housework.task2,
                        task3 = housework.task3,
                        isLiked = !housework.isLiked,
                        lockDurationDays = housework.lockDurationDays,
                        lockExpirationDate = housework.lockExpirationDate,
                        default = housework.default,
                        id = housework.id
                    )
                )

                // Update the housework list and active housework
                viewModel.updateHouseworkList()
                viewModel.getActiveHousework()
            }
        }
    }
}