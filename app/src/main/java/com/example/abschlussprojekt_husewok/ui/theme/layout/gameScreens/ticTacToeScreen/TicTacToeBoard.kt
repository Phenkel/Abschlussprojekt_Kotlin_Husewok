package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.CalcSizes

@Composable
fun TicTacToeBoard(modifier: Modifier = Modifier) {
    // Display the outer Box representing the Tic-Tac-Toe grid
    Box(
        modifier = modifier
            .width(CalcSizes.calcDp(percentage = 0.9f, dimension = CalcSizes.Dimension.Width))
            .aspectRatio(1f)
            .background(
                color = OrangeGrey80.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = CalcSizes.calcDp(percentage = 0.0025f, dimension = CalcSizes.Dimension.Height),
                color = Purple40,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        // Display the rows of the grid
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            repeat(2) {
                // Display each row as a Row composable
                Row(
                    modifier = Modifier
                        .height(
                            CalcSizes.calcDp(
                                percentage = 0.0025f,
                                dimension = CalcSizes.Dimension.Height
                            )
                        )
                        .fillMaxWidth(1f)
                        .background(Purple40)
                ) {
                    // Empty row content
                }
            }
        }

        // Display the columns of the grid
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            repeat(2) {
                // Display each column as a Column composable
                Column(
                    modifier = Modifier
                        .width(
                            CalcSizes.calcDp(
                                percentage = 0.0025f,
                                dimension = CalcSizes.Dimension.Height
                            )
                        )
                        .fillMaxHeight(1f)
                        .background(Purple40)
                ) {
                    // Empty column content
                }
            }
        }
    }
}