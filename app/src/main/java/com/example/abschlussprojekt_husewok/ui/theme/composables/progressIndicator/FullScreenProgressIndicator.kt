package com.example.abschlussprojekt_husewok.ui.theme.composables.progressIndicator

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.utils.CalcSizes

/**
 * A composable function that displays a full-screen progress indicator.
 *
 * @param progress The progress value of the indicator.
 * @param color The color of the indicator.
 */
@Composable
fun FullScreenProgressIndicator(progress: Float, color: Color) {
    ConstraintLayout(
        modifier = Modifier
            .height(CalcSizes.calcDp(percentage = 1f, dimension = CalcSizes.Dimension.Height))
            .width(CalcSizes.calcDp(percentage = 1f, dimension = CalcSizes.Dimension.Width))
    ) {
        val (indicator) = createRefs()

        // Display the circular progress indicator
        CircularProgressIndicator(
            progress = progress,
            color = color,
            modifier = Modifier
                .width(
                    CalcSizes.calcDp(
                        percentage = 0.25f,
                        dimension = CalcSizes.Dimension.Full
                    )
                )
                .height(
                    CalcSizes.calcDp(
                        percentage = 0.25f,
                        dimension = CalcSizes.Dimension.Height
                    )
                )
                .constrainAs(indicator) {
                    centerTo(parent)
                }
        )
    }
}