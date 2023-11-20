package com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.PasswordTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.OnlyContentScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.statics.LoginWatermark
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Composable function for the login screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    // State variables for email, password, and showPassword
    var email by remember { mutableStateOf("philipphnkl@proton.me") }
    var password by remember { mutableStateOf("Ezio130697") }
    var showPassword by remember { mutableStateOf(false) }

    // Retrieve the current context
    val context = LocalContext.current

    // Set up coroutine scope for internet operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Compose the login screen layout
    OnlyContentScaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            // Email text field
            WideTextField(value = email, label = "eMail") { value ->
                email = value
            }

            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)))

            // Password text field
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

            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)))

            // Register button
            WideButton(
                text = "Register",
                icon = Icons.Outlined.AccountCircle,
                primary = false
            ) {
                LoginScreenFunctions.register(
                    viewModel,
                    context,
                    navController,
                    internetScope,
                    email,
                    password
                )
            }

            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)))

            // Login button
            WideButton(
                text = "Login",
                icon = Icons.Outlined.AccountCircle,
                primary = true
            ) {
                LoginScreenFunctions.login(
                    viewModel,
                    context,
                    navController,
                    internetScope,
                    email,
                    password
                )
            }
        }

        // Render the login watermark
        LoginWatermark(padding = innerPadding)
    }
}