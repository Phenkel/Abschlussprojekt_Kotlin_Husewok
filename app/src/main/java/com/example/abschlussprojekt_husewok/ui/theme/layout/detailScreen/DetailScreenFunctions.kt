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
     * Updates an existing task in the housework list. If the input fields are empty, it handles the empty input by
     * displaying an appropriate message. After updating the task, it updates the housework list and navigates
     * to the list screen.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param navController The NavController used for navigating to the list screen.
     * @param context The context used for displaying toast messages.
     * @param housework The existing Housework object to be updated.
     * @param title The new title of the task.
     * @param task1 The new first task description.
     * @param task2 The new second task description.
     * @param task3 The new third task description.
     * @param lockDurationDays The new lock duration in days for the task.
     * @param liked Indicates whether the task is liked.
     */
    fun updateTask(
        viewModel: MainViewModel,
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
        // Check if any of the input fields are empty
        if (title.isEmpty() || task1.isEmpty() || task2.isEmpty() || task3.isEmpty()) {
            // Handle the empty input by displaying an appropriate message
            handleEmptyInput(context)
            return
        }

        // Create a new Housework object with the updated values
        val updatedHousework = housework.copy(
            title = title,
            task1 = task1,
            task2 = task2,
            task3 = task3,
            lockDurationDays = lockDurationDays,
            isLiked = liked
        )

        // Update the existing task in the housework list in firebase
        viewModel.upsertHouseworkFirebase(updatedHousework).addOnSuccessListener {
            // After updating the task, update the housework list in the repository
            viewModel.updateHouseworkList().addOnSuccessListener {
                // Navigate to the list screen
                navController.navigate("list")
            }.addOnFailureListener { exception ->
                // Log a warning if there is a failure in updating the housework list
                Log.w(DETAILFUNCTIONS, "Failed to update housework list", exception)
            }
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in updating the task
            Log.w(DETAILFUNCTIONS, "Failed to upsert housework", exception)
        }
    }

    /**
     * Deletes a task from the housework list based on the provided houseworkId. After deleting the task,
     * it updates the housework list and navigates to the list screen.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param navController The NavController used for navigating to the list screen.
     * @param houseworkId The id of the housework item to be deleted.
     */
    fun deleteTask(
        viewModel: MainViewModel,
        navController: NavController,
        houseworkId: String
    ) {
        // Delete the housework item from the MainViewModel
        viewModel.deleteHousework(houseworkId).addOnSuccessListener {
            // After deleting the task, update the housework list in the MainViewModel
            viewModel.updateHouseworkList().addOnSuccessListener {
                // Navigate to the list screen
                navController.navigate("list")
            }.addOnFailureListener { exception ->
                // Log a warning if there is a failure in updating the housework list
                Log.w(DETAILFUNCTIONS, "Failed to update housework list", exception)
            }
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in deleting the task
            Log.w(DETAILFUNCTIONS, "Failed to delete housework", exception)
        }
    }
}