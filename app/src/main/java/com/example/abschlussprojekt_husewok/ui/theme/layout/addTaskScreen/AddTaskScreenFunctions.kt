package com.example.abschlussprojekt_husewok.ui.theme.layout.addTaskScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the add task screen.
 */
object AddTaskScreenFunctions {
    private const val ADDTASKFUNCTIONS: String = "AddTaskScreenFunction"

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
     * Adds a new task to the housework list. If the input fields are empty, it handles the empty input by
     * displaying an appropriate message. After adding the task, it updates the housework list and navigates
     * to the list screen.
     *
     * @param viewModel The instance of the MainViewModel.
     * @param navController The NavController used for navigating to the list screen.
     * @param context The context used for displaying toast messages.
     * @param title The title of the new task.
     * @param task1 The first task description.
     * @param task2 The second task description.
     * @param task3 The third task description.
     * @param liked Indicates whether the task is liked.
     * @param lockDurationDays The lock duration in days for the task.
     */
    fun addTask(
        viewModel: MainViewModel,
        navController: NavController,
        context: Context,
        title: String,
        task1: String,
        task2: String,
        task3: String,
        liked: Boolean,
        lockDurationDays: Long
    ) {
        // Check if any of the input fields are empty
        if (title.isEmpty() || task1.isEmpty() || task2.isEmpty() || task3.isEmpty()) {
            // Handle the empty input by displaying an appropriate message
            handleEmptyInput(context)
            return
        }

        // Add the new task to the housework list in the MainViewModel
        viewModel.upsertHouseworkFirebase(
            Housework(
                image = R.drawable.img_placeholder,
                title = title,
                task1 = task1,
                task2 = task2,
                task3 = task3,
                isLiked = liked,
                lockDurationDays = lockDurationDays,
                lockExpirationDate = "",
                default = false,
                id = "default"
            )
        ).addOnSuccessListener {
            // After adding the task, update the housework list in the repository
            viewModel.updateHouseworkList().addOnSuccessListener {
                // Navigate to the list screen
                navController.navigate("list")
            }.addOnFailureListener { exception ->
                // Log a warning if there is a failure in updating the housework list
                Log.w(ADDTASKFUNCTIONS, "Failed to update housework list", exception)
            }
        }.addOnFailureListener { exception ->
            // Log a warning if there is a failure in adding the task
            Log.w(ADDTASKFUNCTIONS, "Failed to upsert housework", exception)
        }
    }
}