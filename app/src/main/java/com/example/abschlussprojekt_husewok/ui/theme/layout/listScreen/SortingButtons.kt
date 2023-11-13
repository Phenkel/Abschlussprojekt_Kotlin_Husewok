package com.example.abschlussprojekt_husewok.ui.theme.layout.listScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.SmallButton
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * Composable function to display sorting buttons.
 *
 * @param byLikedOnClick The callback function for when the "Liked" button is clicked.
 * @param byLockedOnClick The callback function for when the "Locked" button is clicked.
 * @param byRandomOnClick The callback function for when the "Random" button is clicked.
 */
@Composable
fun SortingButtons(
    byLikedOnClick: () -> Unit,
    byLockedOnClick: () -> Unit,
    byRandomOnClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            calcDp(percentage = 0.01f, dimension = CalcSizes.Dimension.Height)
        ),
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        // Display the title for sorting
        Text(
            text = "Sort by",
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f),
            fontWeight = FontWeight.Bold
        )

        // Display the sorting buttons
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(
                calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width)
            )
        ) {
            SmallButton(text = "Liked", primary = true, onClick = byLikedOnClick)
            SmallButton(text = "Locked", primary = true, onClick = byLockedOnClick)
            SmallButton(text = "Random", primary = true, onClick = byRandomOnClick)
        }
    }
}