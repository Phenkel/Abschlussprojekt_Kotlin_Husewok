package com.example.abschlussprojekt_husewok.ui.theme.layout.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that displays the login watermark.
 *
 * @param padding The padding values for the watermark.
 */
@Composable
fun LoginWatermark(padding: PaddingValues) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        // Create references for the image and text
        val (image, text) = createRefs()

        // Display the image
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .width(calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width))
                .height(calcDp(percentage = 0.333f, dimension = CalcSizes.Dimension.Height))
                .constrainAs(image) {
                    top.linkTo(parent.top, 16.dp)
                    centerHorizontallyTo(parent)
                }
        )

        // Display the text
        Text(
            text = "Made by Phenkel",
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(parent.bottom, 16.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}