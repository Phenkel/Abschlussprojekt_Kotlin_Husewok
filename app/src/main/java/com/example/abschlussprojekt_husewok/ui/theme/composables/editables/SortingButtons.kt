package com.example.abschlussprojekt_husewok.ui.theme.composables.editables

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
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.SmallButton
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents the sorting buttons for the housework list.
 *
 * @param byLikedOnClick The callback function to invoke when the "Liked" button is clicked.
 * @param byLockedOnClick The callback function to invoke when the "Locked" button is clicked.
 * @param byRandomOnClick The callback function to invoke when the "Random" button is clicked.
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
        // Display the "Sort by" text
        Text(
            text = "Sort by",
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f),
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(
                calcDp(percentage = 0.8f, dimension = CalcSizes.Dimension.Width)
            )
        ) {
            // Display the "Liked" button
            SmallButton(text = "Liked", primary = true, onClick = byLikedOnClick)

            // Display the "Locked" button
            SmallButton(text = "Locked", primary = true, onClick = byLockedOnClick)

            // Display the "Random" button
            SmallButton(text = "Random", primary = true, onClick = byRandomOnClick)
        }
    }
}