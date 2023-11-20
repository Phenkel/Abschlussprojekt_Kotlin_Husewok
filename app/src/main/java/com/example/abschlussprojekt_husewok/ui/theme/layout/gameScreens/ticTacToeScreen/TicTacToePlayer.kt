package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.composables.cards.CardWithAnimatedBorder
import com.example.abschlussprojekt_husewok.utils.CalcSizes

/**
 * Displays the player and AI boxes indicating the current player's turn.
 *
 * @param playerTurn A boolean indicating whether it's the player's turn or not.
 */
@Composable
fun TicTacToePlayer(playerTurn: Boolean, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth(1f)
    ) {
        // Determine the color of the player box based on the current player's turn
        val playerBoxColor = if (playerTurn) Purple40 else Purple40.copy(alpha = 0.5f)

        // Determine the color of the AI box based on the current player's turn
        val aiBoxColor = if (!playerTurn) Orange40 else Orange40.copy(alpha = 0.5f)

        // Display the boxes in player turn
        if (playerTurn) {
            CardWithAnimatedBorder(
                content = {
                    // Display the player box
                    Box(
                        modifier = Modifier
                            .width(CalcSizes.calcDp(0.4f, CalcSizes.Dimension.Width))
                            .height(
                                CalcSizes.calcDp(
                                    percentage = 0.1f,
                                    dimension = CalcSizes.Dimension.Height
                                )
                            )
                            .background(
                                color = playerBoxColor,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Player",
                            fontSize = CalcSizes.calcSp(0.05f),
                            color = Purple80
                        )
                    }
                },
                liked = true
            )

            // Display the AI box
            Box(
                modifier = Modifier
                    .width(CalcSizes.calcDp(0.4f, CalcSizes.Dimension.Width))
                    .height(
                        CalcSizes.calcDp(
                            percentage = 0.1f,
                            dimension = CalcSizes.Dimension.Height
                        )
                    )
                    .background(
                        color = aiBoxColor,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "AI",
                    fontSize = CalcSizes.calcSp(0.05f),
                    color = Orange80
                )
            }
        }
        // Display boxes in ai turn
        else {
            // Display the player box
            Box(
                modifier = Modifier
                    .width(CalcSizes.calcDp(0.4f, CalcSizes.Dimension.Width))
                    .height(
                        CalcSizes.calcDp(
                            percentage = 0.1f,
                            dimension = CalcSizes.Dimension.Height
                        )
                    )
                    .background(
                        color = playerBoxColor,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Player",
                    fontSize = CalcSizes.calcSp(0.05f),
                    color = Purple80
                )
            }

            CardWithAnimatedBorder(
                content = {
                    // Display the AI box
                    Box(
                        modifier = Modifier
                            .width(CalcSizes.calcDp(0.4f, CalcSizes.Dimension.Width))
                            .height(
                                CalcSizes.calcDp(
                                    percentage = 0.1f,
                                    dimension = CalcSizes.Dimension.Height
                                )
                            )
                            .background(
                                color = aiBoxColor,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AI",
                            fontSize = CalcSizes.calcSp(0.05f),
                            color = Orange80
                        )
                    }
                },
                liked = false
            )
        }
    }
}