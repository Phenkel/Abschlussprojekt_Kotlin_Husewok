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

/**
 * A helper object containing functions related to the add task screen.
 */
object AddTaskScreenFunctions {
    private const val ADDTASKFUNCTIONS: String = "AddTaskScreenFunction"

    /**
     * Handles the case when any input field is empty by showing an error toast.
     *
     * @param context The Context instance.
     * @param mainScope The CoroutineScope instance.
     */
    private fun handleEmptyInput(context: Context, mainScope: CoroutineScope) {
        // Show an error toast using MotionToasts library
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
     * Adds a new task to the housework list.
     *
     * @param viewModel The MainViewModel instance.
     * @param navController The NavController instance.
     * @param mainScope The CoroutineScope instance.
     * @param context The Context instance.
     * @param title The title of the new task.
     * @param task1 The first task of the new task.
     * @param task2 The second task of the new task.
     * @param task3 The third task of the new task.
     * @param liked The liked status of the new task.
     * @param lockDurationDays The lock duration in days of the new task.
     */
    fun addTask(
        viewModel: MainViewModel,
        navController: NavController,
        mainScope: CoroutineScope,
        context: Context,
        title: String,
        task1: String,
        task2: String,
        task3: String,
        liked: Boolean,
        lockDurationDays: Long
    ) {
        // Check for empty input fields
        if (title.isEmpty() || task1.isEmpty() || task2.isEmpty() || task3.isEmpty()) {
            handleEmptyInput(context, mainScope)
            return
        }

        // Create a new Housework object with the provided details
        val newHousework = Housework(
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

        // Upsert the new housework object to Firebase
        viewModel.upsertHouseworkFirebase(newHousework).addOnSuccessListener {
            // Update the housework list after successful addition
            viewModel.updateHouseworkList().addOnSuccessListener {
                navController.navigate("list")
            }.addOnFailureListener { exception ->
                Log.w(ADDTASKFUNCTIONS, "Failed to update housework list", exception)
            }
        }.addOnFailureListener { exception ->
            Log.w(ADDTASKFUNCTIONS, "Failed to upsert housework", exception)
        }
    }
}