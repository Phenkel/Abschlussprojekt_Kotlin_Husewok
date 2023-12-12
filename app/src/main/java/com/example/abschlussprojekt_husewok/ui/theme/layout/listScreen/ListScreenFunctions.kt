package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A helper object containing functions related to the list screen.
 */
object ListScreenFunctions {
    private const val LISTFUNCTIONS: String = "ListScreenFunctions"
    private const val FAILED_SORT: String = "Failed to sort housework list"
    private const val FAILED_LOAD: String = "Failed to load housework list"

    /**
     * Shows a success toast using MotionToasts library.
     *
     * @param message The message to be displayed in the toast.
     * @param context The Context in which the toast is shown. Must be an instance of Activity.
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
     * Shows an info toast using MotionToasts library.
     *
     * @param title The title of the toast.
     * @param message The message to be displayed in the toast.
     * @param context The Context in which the toast is shown. Must be an instance of Activity.
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
     * Shows an error toast using MotionToasts library.
     *
     * @param message The message to be displayed in the toast.
     * @param context The Context in which the toast is shown. Must be an instance of Activity.
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
     * Refreshes the housework list by updating it from the view model and displaying appropriate toasts.
     *
     * @param viewModel The MainViewModel instance used to update the housework list.
     * @param context The Context in which the refresh operation is performed.
     * @param mainScope The CoroutineScope used for launching toasts.
     * @param houseworkList The current list of housework items.
     */
    fun refresh(
        viewModel: MainViewModel,
        context: Context,
        mainScope: CoroutineScope,
        houseworkList: List<Housework>
    ) {
        viewModel.updateHouseworkList().addOnSuccessListener {
            // If the housework list is not empty, show a success toast
            if (houseworkList.isNotEmpty()) {
                mainScope.launch {
                    showSuccessToast("Housework list found", context)
                }
            } else {
                // If the housework list is empty, show an error toast and log a warning
                mainScope.launch {
                    showErrorToast("No housework list found. Please reload again", context)
                }
                Log.w(LISTFUNCTIONS, FAILED_LOAD)
            }
        }.addOnFailureListener { exception ->
            // If there is an exception while updating the housework list, show an error toast and log a warning with the exception
            mainScope.launch {
                showErrorToast("No housework list found. Please reload again", context)
            }
            Log.w(LISTFUNCTIONS, FAILED_LOAD, exception)
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
            Log.w(LISTFUNCTIONS, FAILED_SORT, exception)
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
            showInfoToast("Sorted by locked", "Unlocked tasks will be shown first", context)
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in sorting the housework list
            Log.w(LISTFUNCTIONS, FAILED_SORT, exception)
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
            showInfoToast("Shuffled", "Randomized tasks will be shown", context)
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in shuffling the housework list
            Log.w(LISTFUNCTIONS, FAILED_SORT, exception)
        }
    }
}