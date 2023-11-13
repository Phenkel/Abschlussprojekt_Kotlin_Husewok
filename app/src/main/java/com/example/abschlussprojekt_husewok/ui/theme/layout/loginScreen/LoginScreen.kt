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
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.Constants.auth
import com.google.firebase.appcheck.internal.util.Logger.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function to display the login screen.
 *
 * @param navController The NavController used for navigation.
 * @param viewModel The MainViewModel used for data access.
 */
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    // Define mutable state variables for email and password
    var email by remember { mutableStateOf("philipphnkl@proton.me") }
    var password by remember { mutableStateOf("Ezio130697") }

    // Define regular expressions for email and password validation
    val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""".toRegex()
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()

    // Function to check if email is valid
    fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    // Function to check if password is valid
    fun isValidPassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    // Define mutable state variable for showing/hiding password
    var showPassword by remember { mutableStateOf(false) }

    // Create a CoroutineScope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        content = { innerPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                // Display the email input field
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

                // Display the password input field
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
                WideButton(text = "Register", icon = Icons.Outlined.AccountCircle, primary = false) {
                    if (isValidEmail(email) && isValidPassword(password)) {
                        // Register the user and navigate to home screen
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "createUserWithEmail:success")
                                    auth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "signInWithEmail:success")
                                                firebaseScope.launch {
                                                    val user = auth.currentUser
                                                    user?.uid?.let { viewModel.createNewUserFirebase(it) }
                                                }
                                                navController.navigate("home")
                                            } else {
                                                Log.w(TAG, "signInWithEmail:failure", task.exception)
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
                WideButton(text = "Login", icon = Icons.Outlined.AccountCircle, primary = true) {
                    if (isValidEmail(email) && isValidPassword(password)) {
                        // Sign in the user and navigate to home screen
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
            LoginWatermark(padding = innerPadding)
        }
    )
}