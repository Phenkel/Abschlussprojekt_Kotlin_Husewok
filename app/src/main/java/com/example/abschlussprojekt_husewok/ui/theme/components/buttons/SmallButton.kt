package com.example.abschlussprojekt_husewok.ui.theme.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * Composable function to display a small button.
 *
 * @param text The text to display on the button.
 * @param primary Whether the button has a primary color scheme.
 * @param onClick The callback function to handle the button click event.
 */
@Composable
fun SmallButton(text: String, primary: Boolean, onClick: () -> Unit) {
    Button(
        shape = ShapeDefaults.ExtraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) Purple40 else Orange80,
            contentColor = if (primary) Orange80 else Purple40
        ),
        onClick = onClick,
        modifier = Modifier
            .width(calcDp(percentage = 0.25f, dimension = CalcSizes.Dimension.Width))
            .height(calcDp(percentage = 0.05f, dimension = CalcSizes.Dimension.Height))
    ) {
        // Display the button text with specified style
        Text(
            text = text,
            fontSize = calcSp(percentage = 0.0225f)
        )
    }
}