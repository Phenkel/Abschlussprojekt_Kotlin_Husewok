package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.utils.Dimension
import com.example.abschlussprojekt_husewok.utils.calcDp
import com.example.abschlussprojekt_husewok.utils.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple80

/**
 * Composable function to display a housework list card.
 *
 * @param sizeHeight The height of the card.
 * @param sizeWidth The width of the card.
 * @param housework The housework data to display.
 */
@Composable
fun HouseworkListCard(
    sizeHeight: Dp = calcDp(percentage = 0.15f, dimension = Dimension.Height),
    sizeWidth: Dp = calcDp(percentage = 0.8f, dimension = Dimension.Width),
    housework: Housework
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .height(sizeHeight)
            .width(sizeWidth)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Orange80.copy(alpha = 0.7f),
                            Color.White,
                            Purple80.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                // Create references for the image and text row
                val (image, textRow) = createRefs()

                // Display the housework image
                Image(
                    painter = painterResource(id = housework.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.4f)
                        .constrainAs(image) {
                            centerVerticallyTo(parent)
                            start.linkTo(parent.start)
                        },
                    contentScale = ContentScale.Crop
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.6f)
                        .constrainAs(textRow) {
                            end.linkTo(parent.end)
                        }
                ) {
                    // Display the housework title
                    Text(
                        text = housework.title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = calcSp(percentage = 0.035f)
                        )
                    )

                    Spacer(modifier = Modifier.height(sizeHeight * 0.075f))

                    // Display the housework status (done or not done)
                    Text(
                        text = if (housework.isLocked()) "Done" else "Not Done",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = calcSp(percentage = 0.035f)
                        )
                    )
                }
            }
        }
    }
}