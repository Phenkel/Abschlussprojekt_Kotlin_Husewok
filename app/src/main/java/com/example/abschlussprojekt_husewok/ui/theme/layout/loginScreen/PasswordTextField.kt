package com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple80

/**
 * Composable function to display a password text field with show/hide password functionality.
 *
 * @param value The current value of the password field.
 * @param label The label for the password field.
 * @param showPassword The flag indicating whether the password should be shown or hidden.
 * @param onValueChange The callback function to handle the value change of the password field.
 * @param onShowChange The callback function to handle the show/hide password toggle.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    value: String,
    label: String,
    showPassword: Boolean,
    onValueChange: (string: String) -> Unit,
    onShowChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Purple80,
            unfocusedBorderColor = Orange80,
            focusedLabelColor = Purple80,
            unfocusedLabelColor = Orange80,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconToggleButton(
                checked = showPassword,
                onCheckedChange = onShowChange
            ) {
                val icon = if (showPassword) {
                    painterResource(id = R.drawable.ic_eye_filled)
                } else {
                    painterResource(id = R.drawable.ic_eye_outline)
                }
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        label = { Text(text = label) }
    )
}