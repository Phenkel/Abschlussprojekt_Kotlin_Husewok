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

/**
 * A helper object containing functions related to the detail screen.
 */
object DetailScreenFunctions {
    private const val DETAILFUNCTIONS: String = "DetailScreenFunctions"

    /**
     * Handles the case when any input field is empty.
     *
     * @param context The Context instance.
     * @param mainScope The CoroutineScope instance.
     */
    private fun handleEmptyInput(context: Context, mainScope: CoroutineScope) {
        // Show an error toast indicating that all fields must be filled
        mainScope.launch {
            MotionToasts.error(
                title = "Can't add task",
                message = "Please fill out every field",
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Updates the task details in the housework object and saves it to Firebase.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController instance.
     * @param mainScope The CoroutineScope instance.
     * @param context The Context instance.
     * @param housework The original Housework object.
     * @param title The updated task title.
     * @param task1 The updated first task.
     * @param task2 The updated second task.
     * @param task3 The updated third task.
     * @param lockDurationDays The updated lock duration in days.
     * @param liked The updated liked status.
     */
    fun updateTask(
        viewModel: MainViewModel,
        navController: NavController,
        mainScope: CoroutineScope,
        context: Context,
        housework: Housework,
        title: String,
        task1: String,
        task2: String,
        task3: String,
        lockDurationDays: Long,
        liked: Boolean
    ) {
        // Check for empty input fields
        if (title.isEmpty() || task1.isEmpty() || task2.isEmpty() || task3.isEmpty()) {
            handleEmptyInput(context, mainScope)
            return
        }

        // Create a copy of the housework object with updated details
        val updatedHousework = housework.copy(
            title = title,
            task1 = task1,
            task2 = task2,
            task3 = task3,
            lockDurationDays = lockDurationDays,
            isLiked = liked
        )

        // Upsert the updated housework object to Firebase
        viewModel.upsertHouseworkFirebase(updatedHousework).addOnSuccessListener {
            // Update the housework list after successful update
            viewModel.updateHouseworkList().addOnSuccessListener {
                mainScope.launch {
                    // Navigate to the housework list screen
                    navController.navigate("list")
                }
            }.addOnFailureListener { exception ->
                Log.w(DETAILFUNCTIONS, "Failed to update housework list", exception)
            }
        }.addOnFailureListener { exception ->
            Log.w(DETAILFUNCTIONS, "Failed to upsert housework", exception)
        }
    }

    /**
     * Deletes a task from the housework list.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController instance.
     * @param mainScope The CoroutineScope instance.
     * @param houseworkId The ID of the housework to be deleted.
     */
    fun deleteTask(
        viewModel: MainViewModel,
        navController: NavController,
        mainScope: CoroutineScope,
        houseworkId: String
    ) {
        // Delete the housework from Firebase
        viewModel.deleteHousework(houseworkId).addOnSuccessListener {
            // Update the housework list after successful deletion
            viewModel.updateHouseworkList().addOnSuccessListener {
                mainScope.launch {
                    // Navigate to the housework list screen
                    navController.navigate("list")
                }
            }.addOnFailureListener { exception ->
                Log.w(DETAILFUNCTIONS, "Failed to update housework list", exception)
            }
        }.addOnFailureListener { exception ->
            Log.w(DETAILFUNCTIONS, "Failed to delete housework", exception)
        }
    }
}