package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80

// TODO: Documentation
/**
 * Composable function that represents a card for displaying a housework item in a list.
 *
 * @param sizeHeight The height of the card. Default is calculated as a percentage of the screen height.
 * @param sizeWidth The width of the card. Default is calculated as a percentage of the screen width.
 * @param fabOnClick The onClick function for the FloatingActionButton. Default is an empty function.
 * @param housework The housework object to display on the card. Default is a sample housework object.
 *
 * @author Philipp Henkel
 */
@Preview
@Composable
fun HouseworkListCard(
    // Defaut values for size and onClick function
    sizeHeight: Dp = calcDp(percentage = 0.15f, dimension = Dimension.Height),
    sizeWidth: Dp = calcDp(percentage = 0.8f, dimension = Dimension.Width),
    sizeComplete: Dp = (sizeHeight + sizeWidth) / 2,
    fabOnClick: () -> Unit = {},
    // Default housework object
    housework: Housework = Housework(
        image = R.drawable.clean_livingroom,
        title = "Clean Living Room",
        description = "Dust furniture\nVacuum or sweep floors\nWipe down surfaces",
        isLiked = true,
        lockDurationDays = 1
    )
) {
    // Card composable (parent) with custom colors, borders and size
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .height(sizeHeight)
            .width(sizeWidth),
        border = BorderStroke(
            sizeComplete / 100,
            Brush.verticalGradient(listOf(Orange80, Color.White, Purple80))
        )
    ) {
        // Box composable with custom background
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Purple80.copy(alpha = 0.7f),
                            Color.White,
                            Orange80.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {
            // ConstraintLayout composable to position image, verticalCenter, title and status
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(1f)
            ) {
                // Create references for image, verticalCenter, title and status TODO: Aktualisieren
                val (image, imageBorder, logo, textrow, favorite) = createRefs()
                // Image composable with custom size and position
                Image(
                    painter = painterResource(id = housework.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.4f)
                        .constrainAs(image) {
                            centerVerticallyTo(parent)
                            start.linkTo(parent.start)
                        },
                    contentScale = ContentScale.Crop
                )
                // Image composable with custom size and position
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .alpha(0.2f)
                        .constrainAs(logo) {
                            centerVerticallyTo(parent)
                            start.linkTo(imageBorder.end)
                            end.linkTo(parent.end)
                        }
                )
                // Row composable (Housework Title Text and Status Text) with custom size and position
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.6f)
                        .constrainAs(textrow) {
                            end.linkTo(parent.end)
                        }
                ) {
                    // Text composable with custom size and position
                    Text(
                        text = housework.title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = calcSp(percentage = 0.035f)
                        )
                    )
                    // TODO: Kommentar
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight(0.075f)
                    )
                    // Text composable with custom size and position
                    Text(
                        text = if (housework.isLocked()) "Done" else "Not Done",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = calcSp(percentage = 0.035f)
                        )
                    )
                }
                // TODO: Kommentar
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(sizeComplete / 100)
                        .background(Brush.verticalGradient(listOf(Orange80, Color.White, Purple80)))
                        .constrainAs(imageBorder) {
                            start.linkTo(image.end)
                        }
                )
                // TODO: Kommentar
                Icon(
                    imageVector = if (housework.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Purple40,
                    modifier = Modifier
                        .size(sizeComplete / 10)
                        .constrainAs(favorite) {
                        top.linkTo(parent.top, sizeComplete / 25)
                        end.linkTo(parent.end, sizeComplete / 25)
                    }
                )
            }
        }
    }
}