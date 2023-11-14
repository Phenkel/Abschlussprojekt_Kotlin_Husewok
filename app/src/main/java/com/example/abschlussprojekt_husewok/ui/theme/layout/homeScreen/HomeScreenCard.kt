package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.SmallButton
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents a card on the home screen.
 *
 * @param sizeHeight The height of the card.
 * @param sizeWidth The width of the card.
 * @param sizeComplete The average size of the height and width of the card.
 * @param skipButtonEnabled Whether the skip button is enabled.
 * @param skipButtonOnClick The callback function to invoke when the skip button is clicked.
 * @param doneButtonOnClick The callback function to invoke when the done button is clicked.
 * @param fabOnClick The callback function to invoke when the FAB is clicked.
 * @param iconButtonOnClick The callback function to invoke when the icon button is clicked.
 * @param housework The housework object to display in the card.
 */
@Composable
fun HomeScreenCard(
    sizeHeight: Dp = calcDp(percentage = 0.65f, dimension = CalcSizes.Dimension.Height),
    sizeWidth: Dp = calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width),
    sizeComplete: Dp = (sizeHeight + sizeWidth) / 2,
    skipButtonEnabled: Boolean,
    skipButtonOnClick: () -> Unit,
    doneButtonOnClick: () -> Unit,
    fabOnClick: () -> Unit,
    iconButtonOnClick: () -> Unit,
    housework: Housework
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(sizeComplete / 100, Brush.horizontalGradient(listOf(
            Purple80,
            Color.White,
            Orange80
        ))),
        modifier = Modifier
            .height(sizeHeight)
            .width(sizeWidth)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Orange80.copy(alpha = 0.5f),
                            Color.White,
                            Purple80.copy(alpha = 0.5f)
                        )
                    )
                )
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(1F)
            ) {
                val (image, title, button, buttonRow, favorite) = createRefs()

                // Display the housework image
                Image(
                    painter = painterResource(id = housework.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.6f)
                        .constrainAs(image) {
                            centerHorizontallyTo(parent)
                            bottom.linkTo(parent.bottom)
                        },
                    contentScale = ContentScale.Crop
                )

                // Display the skip and done buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .constrainAs(buttonRow) {
                            bottom.linkTo(image.top, sizeHeight / 20)
                        },
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    SmallButton(text = "Skip", primary = false, enabled = skipButtonEnabled, onClick = skipButtonOnClick)
                    SmallButton(text = "Done", primary = true, onClick = doneButtonOnClick)
                }

                // Display the housework title
                Text(
                    text = housework.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = calcSp(percentage = 0.06f)
                    ),
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            bottom.linkTo(buttonRow.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                // Display the FAB
                FloatingActionButton(
                    containerColor = Orange80,
                    contentColor = Purple40,
                    shape = RoundedCornerShape(percent = 10),
                    modifier = Modifier
                        .size((sizeWidth + sizeHeight) / 16)
                        .constrainAs(button) {
                            bottom.linkTo(parent.bottom, margin = sizeComplete / 30)
                            end.linkTo(parent.end, margin = sizeComplete / 30)
                        },
                    onClick = fabOnClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.65f)
                            .padding(0.dp)
                    )
                }

                // Display the favorite icon button
                IconButton(
                    onClick = iconButtonOnClick,
                    modifier = Modifier
                        .size(sizeComplete / 10)
                        .constrainAs(favorite) {
                            top.linkTo(parent.top, sizeComplete / 50)
                            end.linkTo(parent.end, sizeComplete / 50)
                        }
                ) {
                    Icon(
                        imageVector = if (housework.isLiked) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = null,
                        tint = Purple40,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }
            }
        }
    }
}