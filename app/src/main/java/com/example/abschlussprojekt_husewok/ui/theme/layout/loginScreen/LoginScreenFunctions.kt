package com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.Constants.auth
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * A helper object containing functions related to the login screen.
 */
object LoginScreenFunctions {
    private const val LOGINFUNCTIONS = "LoginScreenFunction"
    // Regular expressions for email and password validation
    private val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""".toRegex()
    private val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()

    /**
     * Checks if the email is valid using the email regex.
     *
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     */
    private fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    /**
     * Checks if the password is valid using the password regex.
     *
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private fun isValidPassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    /**
     * Handles the failure of the login process.
     *
     * @param message The error message describing the cause of the failure.
     * @param context The Context used for displaying toasts.
     */
    private fun handleLoginFailure(message: String, context: Context) {
        Log.w(LOGINFUNCTIONS, "signInWithEmail:failure")

        // Show a warning toast for login failure
        MotionToasts.warning(
            title = "Login failed",
            message = message,
            activity = context as Activity,
            context = context
        )
    }

    /**
     * Handles the failure of the registration process.
     *
     * @param message The error message describing the cause of the failure.
     * @param context The Context used for displaying toasts.
     */
    private fun handleRegistrationFailure(message: String, context: Context) {
        Log.w(LOGINFUNCTIONS, "createUserWithEmail:failure")

        // Show a warning toast for registration failure
        MotionToasts.warning(
            title = "Registration failed",
            message = message,
            activity = context as Activity,
            context = context
        )
    }

    /**
     * Performs the login process.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param context The Context used for displaying toasts.
     * @param navController The NavController used for navigating between screens.
     * @param email The email entered by the user.
     * @param password The password entered by the user.
     */
    fun login(
        viewModel: MainViewModel,
        context: Context,
        navController: NavController,
        email: String,
        password: String
    ) {
        // Check if the email and password are valid
        if (!isValidEmail(email) || !isValidPassword(password)) {
            // Handle login failure for invalid credentials
            handleLoginFailure("Email or password invalid", context)
            return
        }

        // Sign in with email and password
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(LOGINFUNCTIONS, "signInWithEmail:success")

                    // Update the current user in the view model
                    val user = auth.currentUser
                    viewModel.updateCurrentUser(user?.uid.toString()).addOnSuccessListener {
                        navController.navigate("home")
                    }
                } else {
                    // Handle login failure with the appropriate error message
                    handleLoginFailure(task.exception?.message.toString(), context)
                }
            }
    }

    /**
     * Performs the registration process.
     *
     * @param viewModel The MainViewModel used for accessing and updating data.
     * @param context The Context used for displaying toasts.
     * @param navController The NavController used for navigating between screens.
     * @param internetScope The CoroutineScope used for the network operation.
     * @param email The email entered by the user.
     * @param password The password entered by the user.
     */
    fun register(
        viewModel: MainViewModel,
        context: Context,
        navController: NavController,
        internetScope: CoroutineScope,
        email: String,
        password: String
    ) {
        // Check if the email and password are valid
        if (!isValidEmail(email) || !isValidPassword(password)) {
            // Handle login failure for invalid credentials
            handleRegistrationFailure("Email or password invalid", context)
            return
        }

        // Create a new user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(LOGINFUNCTIONS, "createUserWithEmail:success")

                    // Sign in the user with the newly created account
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                Log.d(LOGINFUNCTIONS, "signInWithEmail:success")

                                // Perform necessary operations with the user
                                internetScope.launch {
                                    val user = auth.currentUser
                                    user?.uid?.let { viewModel.createNewUserFirebase(it) }
                                }

                                // Navigate to the onboarding screen
                                navController.navigate("onboarding")
                            } else {
                                // Handle sign-in failure
                                handleLoginFailure(signInTask.exception?.message.toString(), context)
                            }
                        }
                } else {
                    // Handle registration failure
                    handleRegistrationFailure(task.exception?.message.toString(), context)
                }
            }
    }

}