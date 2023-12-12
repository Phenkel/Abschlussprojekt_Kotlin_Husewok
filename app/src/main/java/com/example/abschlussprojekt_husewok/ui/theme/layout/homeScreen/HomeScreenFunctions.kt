package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val s = "Please reload the page"

/**
 * A helper object containing functions related to the home screen.
 */
object HomeScreenFunctions {
    private const val HOMEFUNCTIONS: String = "HomeScreenFunctions"
    private val gameDestinations = listOf("tictactoe", "scratching", "colorGuess")

    /**
     * Shows a success toast message using the MotionToasts library.
     *
     * @param title The title of the toast message.
     * @param message The message of the toast. If null, an empty string will be used.
     * @param context The Context instance.
     * @param long Determines whether the toast should be displayed for a longer duration.
     * @param mainScope The CoroutineScope for main thread operations.
     */
    private fun showSuccessToast(
        title: String, message: String?, context: Context, long: Boolean, mainScope: CoroutineScope
    ) {
        mainScope.launch {
            MotionToasts.success(
                title = title,
                message = message ?: "",
                activity = context as Activity,
                context = context,
                long = long
            )
        }
    }

    /**
     * Shows an error toast message using the MotionToasts library.
     *
     * @param title The title of the toast message.
     * @param message The message of the toast.
     * @param context The Context instance.
     * @param mainScope The CoroutineScope for main thread operations.
     */
    private fun showErrorToast(
        title: String, message: String, context: Context, mainScope: CoroutineScope
    ) {
        mainScope.launch {
            MotionToasts.error(
                title = title, message = message, activity = context as Activity, context = context
            )
        }
    }

    /**
     * Shows an info toast message using the MotionToasts library.
     *
     * @param title The title of the toast message.
     * @param message The message of the toast.
     * @param context The Context instance.
     * @param mainScope The CoroutineScope for main thread operations.
     */
    private fun showInfoToast(
        title: String, message: String, context: Context, mainScope: CoroutineScope
    ) {
        mainScope.launch {
            MotionToasts.info(
                title = title, message = message, activity = context as Activity, context = context
            )
        }
    }

    /**
     * Loads the housework data, updates the housework list and active housework,
     * and performs navigation and toast messages based on the loaded data.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController instance.
     * @param context The Context instance.
     * @param mainScope The CoroutineScope for main thread operations.
     */
    fun loadHousework(
        viewModel: MainViewModel,
        navController: NavController,
        context: Context,
        mainScope: CoroutineScope
    ) {
        viewModel.updateHouseworkList().addOnSuccessListener {
            // Housework list update successful
            viewModel.getActiveHousework().addOnSuccessListener {
                // Active housework retrieval successful
                val houseworkList = viewModel.houseworkList.value
                val unlockedHousework = houseworkList.any { !it.isLocked() }
                val housework = viewModel.activeHousework.value

                when {
                    housework?.isLocked() == true || housework?.title == "All done" -> {
                        if (unlockedHousework) {
                            // There are unlocked housework items, navigate to a random game destination
                            val randomGame = gameDestinations.random()
                            navController.navigate(randomGame)
                            viewModel.firstLoaded()
                        } else {
                            // No unlocked housework items, update active housework and show a toast message
                            viewModel.updateActiveHousework(true)
                            showSuccessToast(
                                "All Done", "There is no housework left", context, false, mainScope
                            )
                            viewModel.firstLoaded()
                        }
                    }
                    housework != null -> {
                        // Active housework is not null, show a toast message
                        showSuccessToast(
                            "Housework loaded", housework.title, context, false, mainScope
                        )
                        viewModel.firstLoaded()
                    }
                    else -> {
                        // Active housework is null, show an error toast message and log the error
                        showErrorToast(
                            "Loading error", "Please reload the page", context, mainScope
                        )
                        viewModel.firstLoaded()
                        Log.w(HOMEFUNCTIONS, "Failed to load housework - NULL", NullPointerException("Housework is null"))
                    }
                }
            }.addOnFailureListener { exception ->
                // Active housework retrieval failed, show an error toast message and log the error
                showErrorToast(
                    "Loading error", "Please reload the page", context, mainScope
                )
                Log.w(HOMEFUNCTIONS, "Failed to get active housework", exception)
            }
        }.addOnFailureListener { exception ->
            // Housework list update failed, show an error toast message and log the error
            showErrorToast(
                "Loading error", "Please reload the page", context, mainScope
            )
            Log.w(HOMEFUNCTIONS, "Failed to update housework list", exception)
        }
    }

    /**
     * Handles the skip button functionality by updating user tasks and skip coins,
     * and navigating to a random game destination if the housework is not "All done".
     *
     * @param navController The NavController instance.
     * @param viewModel The MainViewModel instance.
     * @param housework The Housework object.
     */
    fun skipButton(
        navController: NavController, viewModel: MainViewModel, housework: Housework?
    ) {
        // Check if the housework is not "All done"
        if (housework?.title != "All done") {
            // Update user tasks and skip coins
            viewModel.updateUserTasksAndSkipCoins(false)

            // Navigate to a random game destination
            val randomGame = gameDestinations.random()
            navController.navigate(randomGame)
        }
    }

    /**
     * Handles the functionality of the done button by updating user tasks and skip coins,
     * upserting the locked housework, updating the housework list, navigating to a random game destination,
     * and displaying reward toast messages based on the user's reward type.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController instance.
     * @param context The Context instance.
     * @param internetScope The CoroutineScope for internet-related operations.
     * @param mainScope The CoroutineScope for main thread operations.
     * @param housework The Housework object.
     * @param user The User object.
     */
    fun doneButton(
        viewModel: MainViewModel,
        navController: NavController,
        context: Context,
        internetScope: CoroutineScope,
        mainScope: CoroutineScope,
        housework: Housework,
        user: User?
    ) {
        // Check if the housework is not "All done"
        if (housework.title != "All done") {
            // Create a locked housework with updated lock expiration date
            val lockedHousework =
                housework.copy(lockExpirationDate = housework.updateLockExpirationDate())

            // Update user tasks and skip coins
            viewModel.updateUserTasksAndSkipCoins(true)

            // Upsert the locked housework in Firebase
            viewModel.upsertHouseworkFirebase(lockedHousework).addOnSuccessListener {
                // Upsert successful, update housework list
                viewModel.updateHouseworkList().addOnSuccessListener {
                    val houseworkList = viewModel.houseworkList.value
                    val unlockedHousework = houseworkList.any { !it.isLocked() }

                    if (unlockedHousework) {
                        // There are unlocked housework items, navigate to a random game destination
                        val randomGame = gameDestinations.random()
                        navController.navigate(randomGame)
                    } else {
                        // No unlocked housework items, update active housework and show a toast message
                        viewModel.updateActiveHousework(true)
                        showSuccessToast(
                            "All Done", "There is no housework left", context, false, mainScope
                        )
                    }
                }.addOnFailureListener { exception ->
                    // Failed to update housework list, log the error
                    Log.w(HOMEFUNCTIONS, "Failed to update housework list", exception)
                }
            }.addOnFailureListener { exception ->
                // Failed to upsert housework, log the error
                Log.w(HOMEFUNCTIONS, "Failed to upsert housework", exception)
            }
            // Fetch and display reward toast message
            internetScope.launch {
                viewModel.getReward().addOnSuccessListener {
                    if (user?.reward == "Joke") {
                        // User's reward is a joke, display joke toast message
                        viewModel.joke.value.data?.let { joke ->
                            showSuccessToast(
                                "${joke.setup}\n${joke.punchline}",
                                "Joke Reward - ${joke.type}",
                                context,
                                true,
                                mainScope
                            )
                        }
                    } else {
                        // User's reward is a bored activity, display bored activity toast message
                        viewModel.bored.value.data?.let { bored ->
                            showSuccessToast(
                                bored.activity,
                                "Bored Reward - ${bored.participants} / ${bored.type}",
                                context,
                                true,
                                mainScope
                            )
                        }
                    }
                }.addOnFailureListener { exception ->
                    // Failed to fetch reward, log the error
                    Log.w(HOMEFUNCTIONS, "Failed to fetch reward", exception)
                }
            }
        }
    }

    /**
     * Handles the functionality of the favorite button by toggling the "isLiked" property of the housework,
     * and updating the housework list and active housework in Firebase.
     *
     * @param viewModel The MainViewModel instance.
     * @param housework The Housework object.
     */
    fun favoriteButton(
        viewModel: MainViewModel, housework: Housework
    ) {
        // Check if the housework is not "All done"
        if (housework.title != "All done") {
            // Toggle the "isLiked" property of the housework
            val updatedHousework = housework.copy(isLiked = !housework.isLiked)

            // Upsert the updated housework in Firebase
            viewModel.upsertHouseworkFirebase(updatedHousework).addOnSuccessListener {
                // Upsert successful, update housework list
                viewModel.updateHouseworkList().addOnSuccessListener {
                    // Housework list updated successfully, fetch and update active housework
                    viewModel.getActiveHousework().addOnFailureListener { exception ->
                        // Failed to get active housework, log the error
                        Log.w(
                            HOMEFUNCTIONS, "Failed to get active housework", exception
                        )
                    }
                }.addOnFailureListener { exception ->
                    // Failed to update housework list, log the error
                    Log.w(HOMEFUNCTIONS, "Failed to update housework list", exception)
                }
            }.addOnFailureListener { exception ->
                // Failed to upsert housework, log the error
                Log.w(HOMEFUNCTIONS, "Failed to upsert housework", exception)
            }
        }
    }

    /**
     * Handles the functionality of the info button by displaying an information toast message
     * with the tasks and title of the housework.
     *
     * @param context The Context instance.
     * @param mainScope The CoroutineScope for main thread operations.
     * @param housework The Housework object.
     */
    fun infoButton(
        context: Context, mainScope: CoroutineScope, housework: Housework
    ) {
        // Show an information toast message with the tasks and title of the housework
        showInfoToast(
            "${housework.task1}\n${housework.task2}\n${housework.task3}",
            housework.title,
            context,
            mainScope
        )
    }
}