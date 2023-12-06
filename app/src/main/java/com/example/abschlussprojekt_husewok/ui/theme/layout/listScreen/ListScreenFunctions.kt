package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the list screen.
 */
object ListScreenFunctions {
    private const val LISTFUNCTIONS: String = "ListScreenFunctions"
    /**
     * Displays an success toast with the specified message.
     *
     * @param message The message of the toast.
     * @param context The Context used for displaying the toast.
     */
    private fun showSuccessToast(message: String, context: Context) {
        MotionToasts.success(
            title = "Success",
            message = message,
            activity = context as Activity,
            context = context
        )
    }

    /**
     * Displays an info toast with the specified title and message.
     *
     * @param title The title of the toast.
     * @param message The message of the toast.
     * @param context The Context used for displaying the toast.
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
     * Displays an error toast with the specified message.
     *
     * @param message The message of the toast.
     * @param context The Context used for displaying the toast.
     */
    private fun showErrorToast(message: String, context: Context) {
        MotionToasts.error(
            title = "Error",
            message = message,
            activity = context as Activity,
            context = context
        )
    }

    /**
     * Refreshes the housework list by updating it in the view model. Shows a success toast message if the
     * housework list is found and not empty. Shows an error toast message and logs a warning if the housework
     * list is not found or fails to load.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param context The context used for displaying toast messages.
     * @param houseworkList The current housework list.
     */
    fun refresh(
        viewModel: MainViewModel,
        context: Context,
        houseworkList: List<Housework>
    ) {
        // Update the housework list in the view model
        viewModel.updateHouseworkList().addOnSuccessListener {
            if (houseworkList.isNotEmpty()) {
                // Show a success toast message if the housework list is found and not empty
                showSuccessToast("Housework list found", context)
            } else {
                // Show an error toast message and log a warning if the housework list is not found or empty
                showErrorToast("No housework list found. Please reload again", context)
                Log.w(LISTFUNCTIONS, "Failed to load housework list")
            }
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in loading the housework list
            Log.w(LISTFUNCTIONS, "Failed to load housework list", exception)
        }
    }

    /**
     * Sorts the housework list by liked tasks in the repository. Shows an info toast message indicating
     * that the list has been sorted by liked tasks.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param context The context used for displaying toast messages.
     */
    fun sortByLiked(
        viewModel: MainViewModel,
        context: Context
    ) {
        // Sort the housework list by liked tasks in the repository
        viewModel.sortHouseworkList("Liked").addOnSuccessListener {
            // Show an info toast message indicating that the list has been sorted by liked tasks
            showInfoToast("Sorted by liked", "Liked tasks will be shown first", context)
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in sorting the housework list
            Log.w(LISTFUNCTIONS, "Failed to sort housework list", exception)
        }
    }

    /**
     * Sorts the housework list by unlocked tasks in the repository. Shows an info toast message indicating
     * that the list has been sorted by unlocked tasks.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param context The context used for displaying toast messages.
     */
    fun sortByLocked(
        viewModel: MainViewModel,
        context: Context
    ) {
        // Sort the housework list by liked tasks in the repository
        viewModel.sortHouseworkList("Locked").addOnSuccessListener {
            // Show an info toast message indicating that the list has been sorted by unlocked tasks
            showInfoToast("Sorted by liked", "Unlocked tasks will be shown first", context)
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in sorting the housework list
            Log.w(LISTFUNCTIONS, "Failed to sort housework list", exception)
        }
    }

    /**
     * Shuffles the housework list in the repository. Shows an info toast message indicating
     * that the list has been shuffled.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param context The context used for displaying toast messages.
     */
    fun sortByRandom(
        viewModel: MainViewModel,
        context: Context
    ) {
        // Shuffles the housework list in the repository
        viewModel.sortHouseworkList("Random").addOnSuccessListener {
            // Show an info toast message indicating that the list has been shuffled
            showInfoToast("Sorted by liked", "Unlocked tasks will be shown first", context)
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in shuffling the housework list
            Log.w(LISTFUNCTIONS, "Failed to sort housework list", exception)
        }
    }
}