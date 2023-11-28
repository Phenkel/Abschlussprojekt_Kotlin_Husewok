package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.colorGuessScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.CalcSizes

/**
 * Composable function that displays a card to be guessed.
 * This card is a box with a gradient background and no content.
 */
@Composable
fun CardToGuess() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(CalcSizes.calcDp(percentage = 0.6f, dimension = CalcSizes.Dimension.Height))
            .width(CalcSizes.calcDp(percentage = 0.7f, dimension = CalcSizes.Dimension.Width))
            .border(
                CalcSizes.calcDp(percentage = 0.005f, dimension = CalcSizes.Dimension.Full),
                Color.White,
                RoundedCornerShape(20.dp)
            )
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Orange80,
                        Purple40
                    ),
                    tileMode = TileMode.Mirror
                ),
                RoundedCornerShape(20.dp)
            )
    ) {
        // No content inside the box
    }
}