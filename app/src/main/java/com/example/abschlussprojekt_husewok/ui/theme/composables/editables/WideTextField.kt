package com.example.abschlussprojekt_husewok.ui.theme.composables.editables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple80

/**
 * A composable function that represents a wide text field.
 *
 * @param value The current value of the text field.
 * @param label The label text for the text field.
 * @param onValueChange The callback function to invoke when the value of the text field changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WideTextField(value: String, label: String, modifier: Modifier = Modifier ,onValueChange: (string: String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(0.8f),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Purple80,
            unfocusedBorderColor = Orange80,
            focusedLabelColor = Purple80,
            unfocusedLabelColor = Orange80,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        label = { Text(text = label) }
    )
}