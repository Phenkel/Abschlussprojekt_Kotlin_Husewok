package com.example.abschlussprojekt_husewok.ui.theme.layout

import android.content.IntentSender
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.abschlussprojekt_husewok.MainActivity
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.utils.Constants.Companion.auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.appcheck.internal.util.Logger.TAG

@Preview
@Composable
fun previewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }

    val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$""".toRegex()
    fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }

    var password by remember {
        mutableStateOf("")
    }

    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()
    fun isValidPassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    // Create a snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),

        // Display a snackbar
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Purple40,
                    contentColor = Orange80,
                    shape = ShapeDefaults.ExtraSmall
                )
            }
        },

        content = { innerPadding ->

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                OutlinedTextField(value = email,
                    onValueChange = { value ->
                        email = value
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Purple40,
                        unfocusedBorderColor = Orange40,
                        focusedLabelColor = Purple80,
                        unfocusedLabelColor = Orange40,
                        textColor = Color.White
                    ),
                    label = { Text(text = "eMail") })
                Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = Dimension.Height)))

                OutlinedTextField(value = password,
                    onValueChange = { value ->
                        password = value
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Purple40,
                        unfocusedBorderColor = Orange40,
                        focusedLabelColor = Purple80,
                        unfocusedLabelColor = Orange40,
                        textColor = Color.White
                    ),
                    label = { Text(text = "Password") })
                Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = Dimension.Height)))

                // TODO: REGISTER
                Button(shape = ShapeDefaults.ExtraSmall, colors = ButtonDefaults.buttonColors(
                    containerColor = Orange80, contentColor = Purple40
                ), modifier = Modifier
                    .width(
                        calcDp(
                            percentage = 0.8f, dimension = Dimension.Width
                        )
                    )
                    .height(
                        calcDp(
                            percentage = 0.05f, dimension = Dimension.Height
                        )
                    ), onClick = {
                        if (isValidEmail(email) && isValidPassword(password)) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "createUserWithEmail:success")
                                        val user = auth.currentUser
                                        // TODO: Register
                                        navController.navigate("home")
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                        // TODO: SNACKBAR
                                    }
                                }
                        } else {
                            Log.w(TAG, "emailOrPasswordInvalid")
                            // TODO: SNACKBAR
                        }
                }) {
                    Text(
                        text = "Register"
                    )
                }
                Spacer(modifier = Modifier.height(calcDp(percentage = 0.02f, dimension = Dimension.Height)))

                Button(shape = ShapeDefaults.ExtraSmall, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40, contentColor = Orange80
                ), modifier = Modifier
                    .width(
                        calcDp(
                            percentage = 0.8f, dimension = Dimension.Width
                        )
                    )
                    .height(
                        calcDp(
                            percentage = 0.05f, dimension = Dimension.Height
                        )
                    ), onClick = {
                    if (isValidEmail(email) && isValidPassword(password)) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "signInWithEmail:success")
                                    val user = auth.currentUser
                                    // TODO: Login
                                    navController.navigate("home")
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                    // TODO: SNACKBAR
                                }
                            }
                    } else {
                        Log.w(TAG, "emailOrPasswordInvalid")
                        // TODO: SNACKBAR
                    }
                }) {
                    Text(
                        text = "Login"
                    )
                }
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                val (image, text) = createRefs()

                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            centerHorizontallyTo(parent)
                        }
                        .size(calcDp(percentage = 0.8f, dimension = Dimension.Width)))
                Text(text = "Made by Phenkel",
                    color = Color.White,
                    fontSize = calcSp(percentage = 0.05f),
                    modifier = Modifier.constrainAs(text) {
                        bottom.linkTo(parent.bottom)
                        centerHorizontallyTo(parent)
                    })
            }
        })
}