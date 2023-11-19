package com.example.abschlussprojekt_husewok.ui.theme.composables.editables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.SmallButton
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents a row displaying a reward.
 *
 * @param reward The reward text to display.
 * @param onClick The callback function to execute when the reward row is clicked.
 */
@Composable
fun RewardRow(reward: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        // Display the reward label
        Text(
            text = "Reward:",
            fontSize = calcSp(percentage = 0.033f),
            color = Color.White
        )

        // Display the small button with the reward text
        SmallButton(text = reward, primary = reward == "Joke", onClick = onClick)
    }
}