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

/**
 * A helper object containing functions related to the login screen.
 */
object LoginScreenFunctions {
    private const val LOGINFUNCTIONS = "LoginScreenFunction"
    /**
     * Regular expression for validating an email address.
     *
     * The email address must have the following format:
     * - Starts with one or more alphanumeric characters, dots, underscores, percentage signs, plus signs, or hyphens.
     * - Followed by the '@' symbol.
     * - Followed by one or more alphanumeric characters, dots, or hyphens.
     * - Ends with a domain extension consisting of two to six alphabetic characters.
     */
    private val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""".toRegex()

    /**
     * Regular expression for validating a password.
     *
     * The password must have the following format:
     * - Contains at least one alphabetic character (uppercase or lowercase).
     * - Contains at least one digit.
     * - Consists of a minimum of eight characters.
     */
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
     * Handles the failure of a login operation.
     *
     * @param message The error message to display.
     * @param context The context in which the login failure occurred.
     * @param mainScope The coroutine scope to launch the toast in.
     */
    private fun handleLoginFailure(message: String, context: Context, mainScope: CoroutineScope) {
        Log.w(LOGINFUNCTIONS, "signInWithEmail:failure")

        // Launch a coroutine in the main scope to show a warning toast
        mainScope.launch {
            MotionToasts.warning(
                title = "Login failed",
                message = message,
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Handles the failure of a registration operation.
     *
     * @param message The error message to display.
     * @param context The context in which the registration failure occurred.
     * @param mainScope The coroutine scope to launch the toast in.
     */
    private fun handleRegistrationFailure(message: String, context: Context, mainScope: CoroutineScope) {
        Log.w(LOGINFUNCTIONS, "createUserWithEmail:failure")

        // Launch a coroutine in the main scope to show a warning toast
        mainScope.launch {
            MotionToasts.warning(
                title = "Registration failed",
                message = message,
                activity = context as Activity,
                context = context
            )
        }
    }

    /**
     * Performs a login operation.
     *
     * @param viewModel The view model used to update the current user.
     * @param context The context in which the login operation is performed.
     * @param mainScope The coroutine scope to launch the toast and navigation operations in.
     * @param navController The navigation controller used to navigate to the home screen.
     * @param email The email of the user.
     * @param password The password of the user.
     */
    fun login(
        viewModel: MainViewModel,
        context: Context,
        mainScope: CoroutineScope,
        navController: NavController,
        email: String,
        password: String
    ) {
        // Check if the email and password are valid
        if (!isValidEmail(email) || !isValidPassword(password)) {
            handleLoginFailure("Email or password invalid", context, mainScope)
            return
        }

        // Perform login with Firebase authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(LOGINFUNCTIONS, "signInWithEmail:success")

                    // Update the current user in the view model
                    val user = auth.currentUser
                    viewModel.updateCurrentUser(user?.uid.toString()).addOnSuccessListener {

                        // Navigate to the home screen
                        mainScope.launch {
                        navController.navigate("home")
                        }
                    }
                } else {
                    // Handle login failure
                    handleLoginFailure(task.exception?.message.toString(), context, mainScope)
                }
            }
    }

    /**
     * Performs a registration operation.
     *
     * @param viewModel The view model used to create a new user.
     * @param context The context in which the registration operation is performed.
     * @param navController The navigation controller used to navigate to the onboarding screen.
     * @param mainScope The coroutine scope to launch the toast and navigation operations in.
     * @param email The email of the user.
     * @param password The password of the user.
     */
    fun register(
        viewModel: MainViewModel,
        context: Context,
        navController: NavController,
        mainScope: CoroutineScope,
        email: String,
        password: String
    ) {
        // Check if the email and password are valid
        if (!isValidEmail(email) || !isValidPassword(password)) {
            handleRegistrationFailure("Email or password invalid", context, mainScope)
            return
        }

        // Perform registration with Firebase authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(LOGINFUNCTIONS, "createUserWithEmail:success")

                    // Sign in after registration
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                Log.d(LOGINFUNCTIONS, "signInWithEmail:success")

                                // Create a new user in the view model
                                val user = auth.currentUser
                                user?.uid?.let { viewModel.createNewUserFirebase(it) }

                                // Navigate to the onboarding screen
                                mainScope.launch {
                                    navController.navigate("onboarding")
                                }
                            } else {
                                // Handle login failure after registration
                                handleLoginFailure(signInTask.exception?.message.toString(), context, mainScope)
                            }
                        }
                } else {
                    // Handle registration failure
                    handleRegistrationFailure(task.exception?.message.toString(), context, mainScope)
                }
            }
    }
}