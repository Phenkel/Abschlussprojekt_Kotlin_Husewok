package com.example.abschlussprojekt_husewok.ui.theme.composables.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents a small button.
 *
 * @param text The text to display on the button.
 * @param primary Whether the button is in the primary color scheme.
 * @param enabled Whether the button is enabled or disabled. Default is true.
 * @param onClick The callback function to invoke when the button is clicked.
 */
@Composable
fun SmallButton(text: String, primary: Boolean, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        shape = ShapeDefaults.ExtraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) Purple40 else Orange80,
            contentColor = if (primary) Orange80 else Purple40,
            disabledContainerColor = if (primary) Purple80.copy(alpha = 0.5f) else Orange40.copy(alpha = 0.5f),
            disabledContentColor = if (primary) Orange40 else Purple80
        ),
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .width(calcDp(percentage = 0.25f, dimension = CalcSizes.Dimension.Width))
            .height(calcDp(percentage = 0.05f, dimension = CalcSizes.Dimension.Height))
    ) {
        Text(
            text = text,
            fontSize = calcSp(percentage = 0.024f),
            softWrap = false
        )
    }
}