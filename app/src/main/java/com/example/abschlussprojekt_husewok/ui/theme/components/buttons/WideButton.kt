package com.example.abschlussprojekt_husewok.ui.theme.components.buttons

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
import com.example.abschlussprojekt_husewok.utils.Dimension
import com.example.abschlussprojekt_husewok.utils.calcDp
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.calcSp

/**
 * Composable function to display a wide button with an icon and text.
 *
 * @param text The text to display on the button.
 * @param icon The icon to display on the button.
 * @param primary Whether the button has a primary color scheme.
 * @param onClick The callback function to handle the button click event.
 */
@Composable
fun WideButton(text: String, icon: ImageVector, primary: Boolean, onClick: () -> Unit) {
    Button(
        shape = ShapeDefaults.ExtraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) Purple40 else Orange80,
            contentColor = if (primary) Orange80 else Purple40
        ),
        modifier = Modifier
            .width(calcDp(percentage = 0.8f, dimension = Dimension.Width))
            .height(calcDp(percentage = 0.05f, dimension = Dimension.Height)),
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
                fontSize = calcSp(percentage = 0.0225f),
                modifier = Modifier.constrainAs(textRef) {
                    centerTo(parent)
                }
            )
        }
    }
}