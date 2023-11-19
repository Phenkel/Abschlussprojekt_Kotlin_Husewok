package com.example.abschlussprojekt_husewok.ui.theme.composables.statics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents a row displaying user information.
 *
 * @param label The label text for the user information.
 * @param value The value text for the user information.
 */
@Composable
fun UserInfoRow(label: String, value: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        // Display the label text
        Text(
            text = label,
            fontSize = calcSp(percentage = 0.033f),
            color = Color.White
        )

        // Display the value text, or an empty string if value is null
        Text(
            text = value ?: "",
            fontSize = calcSp(percentage = 0.033f),
            color = Color.White
        )
    }
}