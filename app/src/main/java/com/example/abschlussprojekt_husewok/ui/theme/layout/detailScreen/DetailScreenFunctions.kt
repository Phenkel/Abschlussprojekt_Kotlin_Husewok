package com.example.abschlussprojekt_husewok.ui.theme.layout.detailScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the detail screen.
 */
object DetailScreenFunctions {
    private const val DETAILFUNCTIONS: String = "DetailScreenFunctions"
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
     * Handles the case when input fields are empty.
     *
     * @param context The current context.
     */
    private fun handleEmptyInput(context: Context) {
        MotionToasts.error(
            title = "Can't add task",
            message = "Please fill out every field",
            activity = context as Activity,
            context = context
        )
    }

    /**
     * Updates a task in the housework list.
     *
     * @param viewModel The MainViewModel instance for accessing data and business logic.
     * @param internetScope The CoroutineScope for internet operations.
     * @param navController The NavController for navigating between screens.
     * @param context The current context.
     * @param housework The original Housework object to be updated.
     * @param title The updated title of the task.
     * @param task1 The updated first task description.
     * @param task2 The updated second task description.
     * @param task3 The updated third task description.
     * @param lockDurationDays The updated lock duration of the task in days.
     * @param liked The updated liked status of the task.
     */
    suspend fun updateTask(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        navController: NavController,
        context: Context,
        housework: Housework,
        title: String,
        task1: String,
        task2: String,
        task3: String,
        lockDurationDays: Long,
        liked: Boolean
    ) {
        // Check if any input fields are empty
        if (title.isEmpty() || task1.isEmpty() || task2.isEmpty() || task3.isEmpty()) {
            handleEmptyInput(context)
            return
        }

        // Create a copy of the housework with updated values
        val updatedHousework = housework.copy(
            title = title,
            task1 = task1,
            task2 = task2,
            task3 = task3,
            lockDurationDays = lockDurationDays,
            isLiked = liked
        )

        // Upsert the updated housework in Firebase
        val upsertHouseworkSuccess = suspendOperation(internetScope) {
            viewModel.upsertHouseworkFirebase(updatedHousework)
        }

        // Check if the upsert operation was successful
        if (upsertHouseworkSuccess) {
            // Update the housework list
            internetScope.launch { viewModel.updateHouseworkList() }

            // Navigate to the list screen
            navController.navigate("list")
        } else {
            Log.w(DETAILFUNCTIONS, "Failed to upsert housework")
        }
    }

    /**
     * Deletes a task from the housework list.
     *
     * @param viewModel The MainViewModel instance for accessing data and business logic.
     * @param internetScope The CoroutineScope for internet operations.
     * @param navController The NavController for navigating between screens.
     * @param houseworkId The ID of the housework to be deleted.
     */
    suspend fun deleteTask(
        viewModel: MainViewModel,
        internetScope: CoroutineScope,
        navController: NavController,
        houseworkId: String
    ) {
        // Delete the housework from the database
        val deleteHouseworkSuccess = suspendOperation(internetScope) {
            viewModel.deleteHousework(houseworkId)
        }

        // Check if the delete operation was successful
        if (deleteHouseworkSuccess) {
            // Update the housework list
            internetScope.launch { viewModel.updateHouseworkList() }

            // Navigate to the list screen
            navController.navigate("list")
        } else {
            Log.w(DETAILFUNCTIONS, "Failed to delete housework")
        }
    }
}