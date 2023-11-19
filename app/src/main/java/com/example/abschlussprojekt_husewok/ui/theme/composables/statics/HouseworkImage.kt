package com.example.abschlussprojekt_husewok.ui.theme.composables.statics

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp

/**
 * A composable function that displays a housework image.
 *
 * @param image The resource ID of the image to display.
 */
@Composable
fun HouseworkImage(image: Int) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width))
            .height(calcDp(percentage = 0.333f, dimension = CalcSizes.Dimension.Height))
            .border(
                width = calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width) / 100,
                brush = Brush.horizontalGradient(
                    colors = listOf(Orange80, Color.White, Purple80)
                ),
                shape = ShapeDefaults.ExtraSmall
            )
    )
}