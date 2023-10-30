package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80

/** TODO: Aktualisieren
 * A composable function that represents a card on the home screen.
 *
 * @param sizeHeight The height of the card. By default, it is calculated as a percentage of the screen height.
 * @param sizeWidth The width of the card. By default, it is calculated as a percentage of the screen width.
 * @param sizeComplete The complete size of the card, calculated by adding the height and width together and dividing with 2.
 * @param skipButtonOnClick The onClick function for the Skip button. By default, it is an empty function.
 * @param doneButtonOnClick The onClick function for the Done button. By default, it is an empty function.
 * @param fabOnClick The onClick function for the FloatingActionButton. By default, it is an empty function.
 * @param iconButtonOnClick The onClick function for the IconButton. By default, it is an empty function.
 * @param housework The default housework object to display on the card. By default, it is a random housework object.
 *
 * @author Philipp Henkel
 */
@Preview
@Composable
fun HomescreenCard(
    // Default values for size and onClick functions
    sizeHeight: Dp = calcDp(percentage = 0.65f, dimension = Dimension.Height),
    sizeWidth: Dp = calcDp(percentage = 0.8f, dimension = Dimension.Width),
    sizeComplete: Dp = (sizeHeight + sizeWidth) / 2,
    skipButtonEnabled: Boolean = true,
    skipButtonOnClick: () -> Unit = {},
    doneButtonOnClick: () -> Unit = {},
    fabOnClick: () -> Unit = {},
    iconButtonOnClick: () -> Unit = {},
    // Default housework object
    housework: Housework = Housework(
        image = R.drawable.clean_floors,
        title = "Clean Floors",
        task1 = "Pick everything up from floors",
        task2 = "Vacuum floors",
        task3 = "Mop floors",
        lockDurationDays = 7
    )
) {
    // Card composable (Parent) with custom colors, border, and size
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .height(sizeHeight)
            .width(sizeWidth),
        border = BorderStroke(
            sizeComplete / 100, Brush.horizontalGradient(listOf(Orange80, Color.White, Purple80))
        )
    ) {
        // Box composable (Brush background) with custom background
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
            // ConstraintLayout composable to position image, title, and button
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(1F)
            ) {
                // Create references for image, title, button, buttonRow and favorite //TODO: kommentar
                val (image, border, title, button, buttonRow, logo, favorite) = createRefs()
                // Image composable (Housework Image) with custom size, position and border
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
                // TODO: Kommentar
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(sizeComplete / 100)
                        .background(brush = Brush.horizontalGradient(listOf(Orange80, Color.White, Purple80)))
                        .constrainAs(border) {
                            bottom.linkTo(image.top)
                        }
                )
                // Row composable (Skip Task Button and Mark as Done Button) with custom size and position
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .constrainAs(buttonRow) {
                            bottom.linkTo(image.top, sizeHeight / 20)
                        },
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Button composable (Skip Task) for the row with custom shape colors and padding TODO: Aktualisieren
                    Button(
                        enabled = skipButtonEnabled,
                        onClick = skipButtonOnClick,
                        shape = ShapeDefaults.ExtraSmall,
                        contentPadding = PaddingValues(sizeComplete / 50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange80,
                            contentColor = Purple40,
                            disabledContainerColor = Orange40.copy(alpha = 0.5f),
                            disabledContentColor = Purple80
                        ),
                        modifier = Modifier
                            .height(sizeHeight / 10 * 3 / 4)
                    ) {
                        // Icon composable (Skip) for the button
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null
                        )
                        // Spacer composable (Padding) for the button
                        Spacer(modifier = Modifier.width(sizeComplete / 50))
                        // Text composable (Text) for the button
                        Text(
                            text = "Skip Task"
                        )
                    }
                    // Button composable (Skip Task) for the row with custom shape colors and padding TODO: Aktualisieren
                    Button(
                        onClick = doneButtonOnClick,
                        shape = ShapeDefaults.ExtraSmall,
                        contentPadding = PaddingValues(sizeComplete / 50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple40,
                            contentColor = Orange80
                        ),
                        modifier = Modifier
                            .height(sizeHeight / 10 * 3 / 4)
                    ) {
                        // Icon composable (Done) for the button
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = null
                        )
                        // Spacer composable (Padding) for the button
                        Spacer(modifier = Modifier.width(sizeComplete / 50))
                        // Text composable (Text) for the button
                        Text(
                            text = "Mark as Done"
                        )
                    }
                }
                // Text composable (Housework Title) with custom size and position
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
                // FloatingActionButton composable (Information Button) with custom size, position, shape and colors
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
                    // Icon composable (Info) for the FloatingActionButton with custom size and padding
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.65f)
                            .padding(0.dp)
                    )
                }
                // IconButton composable (Favorite) with custom size and position
                IconButton(
                    onClick = iconButtonOnClick,
                    modifier = Modifier
                        .size(sizeComplete / 10)
                        .constrainAs(favorite) {
                            top.linkTo(parent.top, sizeComplete / 50)
                            end.linkTo(parent.end, sizeComplete / 50)
                        }
                ) {
                    // Icon composable (Info) with custom size
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