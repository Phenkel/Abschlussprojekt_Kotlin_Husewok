package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.utils.CalcSizes

/**
 * Composable function that displays the Tic-Tac-Toe icons on a grid.
 *
 * @param moves The list of moves in the Tic-Tac-Toe game.
 * @param modifier The modifier for the parent layout.
 * @param emptyIcon The composable function that represents the empty icon.
 */
@Composable
fun TicTacToeIcons(
    moves: List<Boolean?>,
    modifier: Modifier = Modifier,
    emptyIcon: @Composable (move: Int) -> Unit
) {
    Column(
        modifier = modifier
            .width(CalcSizes.calcDp(percentage = 0.9f, dimension = CalcSizes.Dimension.Width))
            .aspectRatio(1f)
    ) {
        for (i in 0..2) {
            Row(modifier = Modifier.weight(1f)) {
                for (j in 0..2) {
                    Column(modifier = Modifier.weight(1f)) {
                        val move = i * 3 + j
                        when (moves[move]) {
                            true -> {
                                // Display the circle icon for a player move
                                Image(
                                    painter = painterResource(id = R.drawable.ic_empty_circle),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(1f),
                                    colorFilter = ColorFilter.tint(Purple80)
                                )
                            }
                            false -> {
                                // Display the cross icon for a ai move
                                Image(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(1f),
                                    colorFilter = ColorFilter.tint(Orange80)
                                )
                            }
                            null -> {
                                // Call the emptyIcon composable for an empty move
                                emptyIcon(move)
                            }
                        }
                    }
                }
            }
        }
    }
}