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
     * Refreshes the housework list by updating it in the view model.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param internetScope The CoroutineScope used for the network operation.
     * @param context The Context used for displaying toasts.
     * @param houseworkList The list of housework items.
     */
    suspend fun refresh(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        context: Context,
        houseworkList: List<Housework>
    ) {
        // Execute the suspend operation to update the housework list in the view model
        val isSuccess = suspendOperation(internetScope) {
            viewModel.updateHouseworkList()
        }

        // Check the result of the operation
        if (isSuccess) {
            // If the housework list is not empty, show a success toast
            if (houseworkList.isNotEmpty()) {
                showSuccessToast("Housework list found", context)
            } else {
                Log.w(LISTFUNCTIONS, "Failed to load housework list")

                // If the housework list is empty, show an error toast
                showErrorToast("No housework list found. Please reload again", context)
            }
        } else {
            Log.w(LISTFUNCTIONS, "Failed to load housework list")

            // If the operation fails, show an error toast
            showErrorToast("Failed to refresh housework list", context)
        }
    }

    /**
     * Sorts the housework list by the "Liked" criteria.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param internetScope The CoroutineScope used for the network operation.
     * @param context The Context used for displaying toasts.
     */
    suspend fun sortByLiked(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        context: Context
    ) {
        val isSuccess = suspendOperation(internetScope) {
            viewModel.sortHouseworkList("Liked")
        }

        if (isSuccess) {
            showInfoToast("Sorted by liked", "Liked tasks will be shown first", context)
        } else {
            Log.w(LISTFUNCTIONS, "Failed to sort housework list")
            showErrorToast("Failed to sort by liked", context)
        }
    }

    /**
     * Sorts the housework list by the "Locked" criteria.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param internetScope The CoroutineScope used for the network operation.
     * @param context The Context used for displaying toasts.
     */
    suspend fun sortByLocked(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        context: Context
    ) {
        val isSuccess = suspendOperation(internetScope) {
            viewModel.sortHouseworkList("Locked")
        }

        if (isSuccess) {
            showInfoToast("Sorted by locked", "Unlocked tasks will be shown first", context)
        } else {
            Log.w(LISTFUNCTIONS, "Failed to sort housework list")
            showErrorToast("Failed to sort by locked", context)
        }
    }

    /**
     * Shuffles the housework list randomly.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param internetScope The CoroutineScope used for the network operation.
     * @param context The Context used for displaying toasts.
     */
    suspend fun sortByRandom(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        context: Context
    ) {
        val isSuccess = suspendOperation(internetScope) {
            viewModel.sortHouseworkList("Random")
        }

        if (isSuccess) {
            showInfoToast("Housework list shuffled", "Have fun searching", context)
        } else {
            Log.w(LISTFUNCTIONS, "Failed to sort housework list")
            showErrorToast("Failed to shuffle the list", context)
        }
    }
}