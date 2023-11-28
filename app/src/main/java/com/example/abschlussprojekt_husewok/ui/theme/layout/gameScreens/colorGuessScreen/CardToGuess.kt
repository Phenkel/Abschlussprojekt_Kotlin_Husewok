package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.colorGuessScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.utils.CalcSizes

/**
 * Composable function that displays a card with a gradient background and text.
 *
 * @param primary The primary color of the gradient.
 * @param secondary The secondary color of the gradient.
 */
@Composable
fun GuessedCard(primary: Color, secondary: Color) {
    Box(
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
                    listOf(
                        primary,
                        secondary
                    ),
                    tileMode = TileMode.Mirror
                ),
                RoundedCornerShape(20.dp)
            )
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(1f)
        ) {
            val (topText, bottomText, image) = createRefs()

            // Image
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .width(CalcSizes.calcDp(percentage = 0.35f, dimension = CalcSizes.Dimension.Width))
                    .constrainAs(image) {
                        centerTo(parent)
                    }
            )

            // Top Text
            Text(
                text = "Hûséwøk",
                fontSize = CalcSizes.calcSp(percentage = 0.07f),
                color = Color.White,
                modifier = Modifier
                    .constrainAs(topText) {
                        top.linkTo(parent.top, 16.dp)
                        centerHorizontallyTo(parent)
                    }
            )

            // Bottom Text
            Text(
                text = "Hûséwøk",
                fontSize = CalcSizes.calcSp(percentage = 0.07f),
                color = Color.White,
                modifier = Modifier
                    .rotate(180f)
                    .constrainAs(bottomText) {
                        bottom.linkTo(parent.bottom, 16.dp)
                        centerHorizontallyTo(parent)
                    }
            )
        }
    }
}