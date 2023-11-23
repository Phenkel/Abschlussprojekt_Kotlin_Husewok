package com.example.abschlussprojekt_husewok.ui.theme.composables.buttons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents a wide button with an icon and text.
 *
 * @param text The text to display on the button.
 * @param icon The icon to display on the button.
 * @param primary Whether the button is in the primary color scheme.
 * @param onClick The callback function to invoke when the button is clicked.
 */
@Composable
fun WideButton(
    text: String,
    icon: ImageVector,
    primary: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        shape = ShapeDefaults.ExtraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) Purple40 else Orange80,
            contentColor = if (primary) Orange80 else Purple40
        ),
        enabled = enabled,
        modifier = modifier
            .width(calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width))
            .height(calcDp(percentage = 0.05f, dimension = CalcSizes.Dimension.Height)),
        onClick = onClick
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(1f)
        ) {
            val (iconRef, textRef) = createRefs()

            // Display the icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.constrainAs(iconRef) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            // Display the text
            Text(
                text = text,
                fontSize = calcSp(percentage = 0.03f),
                modifier = Modifier.constrainAs(textRef) {
                    centerTo(parent)
                }
            )
        }
    }
}