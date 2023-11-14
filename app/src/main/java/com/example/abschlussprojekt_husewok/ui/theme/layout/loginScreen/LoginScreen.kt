package com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.components.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.OnlyContentScaffold
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.Constants.auth
import com.google.firebase.appcheck.internal.util.Logger.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A composable function that represents the login screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing user data and performing actions.
 */
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    // State variables for email and password
    var email by remember { mutableStateOf("philipphnkl@proton.me") }
    var password by remember { mutableStateOf("Ezio130697") }

    // Regular expressions for email and password validation
    val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""".toRegex()
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()

    /**
     * Checks if the email is valid using the email regex.
     *
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     */
    fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    /**
     * Checks if the password is valid using the password regex.
     *
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    fun isValidPassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    // State variable for showing or hiding the password
    var showPassword by remember { mutableStateOf(false) }

    // Create a coroutine scope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Compose the login screen UI using OnlyContentScaffold
    OnlyContentScaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            // Display the email text field
            WideTextField(value = email, label = "eMail") { value ->
                email = value
            }

            Spacer(
                modifier = Modifier.height(
                    calcDp(
                        percentage = 0.02f, dimension = CalcSizes.Dimension.Height
                    )
                )
            )

            // Display the password text field
            PasswordTextField(
                value = password,
                label = "Password a-Z 0-9",
                showPassword = showPassword,
                onValueChange = { value ->
                    password = value
                },
                onShowChange = { value ->
                    showPassword = value
                }
            )

            Spacer(
                modifier = Modifier.height(
                    calcDp(
                        percentage = 0.02f, dimension = CalcSizes.Dimension.Height
                    )
                )
            )

            // Display the register button
            WideButton(
                text = "Register",
                icon = Icons.Outlined.AccountCircle,
                primary = false
            ) {
                if (isValidEmail(email) && isValidPassword(password)) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "createUserWithEmail:success")
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { signInTask ->
                                        if (signInTask.isSuccessful) {
                                            Log.d(TAG, "signInWithEmail:success")
                                            firebaseScope.launch {
                                                val user = auth.currentUser
                                                user?.uid?.let { viewModel.createNewUserFirebase(it) }
                                            }
                                            navController.navigate("home")
                                        } else {
                                            Log.w(TAG, "signInWithEmail:failure", signInTask.exception)
                                        }
                                    }
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            }
                        }
                } else {
                    Log.w(TAG, "emailOrPasswordInvalid")
                }
            }

            Spacer(
                modifier = Modifier.height(
                    calcDp(
                        percentage = 0.02f, dimension = CalcSizes.Dimension.Height
                    )
                )
            )

            // Display the login button
            WideButton(
                text = "Login",
                icon = Icons.Outlined.AccountCircle,
                primary = true
            ) {
                if (isValidEmail(email) && isValidPassword(password)) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "signInWithEmail:success")
                                firebaseScope.launch {
                                    val user = auth.currentUser
                                    viewModel.updateCurrentUser(user?.uid.toString())
                                }
                                navController.navigate("home")
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                            }
                        }
                } else {
                    Log.w(TAG, "emailOrPasswordInvalid")
                }
            }
        }

        // Display the login watermark
        LoginWatermark(padding = innerPadding)
    }
}