package com.example.abschlussprojekt_husewok.ui.theme.layout.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * Composable function to display a row for the reward.
 *
 * @param reward The reward value to display.
 * @param onClick The callback function to handle the button click event.
 */
@Composable
fun RewardRow(reward: String?, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {

        // Display the label for the reward
        Text(
            text = "Reward:",
            fontSize = calcSp(percentage = 0.033f),
            color = Color.White
        )

        // Display the button with the reward value
        Button(
            shape = ShapeDefaults.ExtraSmall,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(

                // Set the container and content color based on the reward value
                containerColor = if (reward == "Joke") Purple40 else Orange80,
                contentColor = if (reward == "Joke") Orange80 else Purple40
            ),
            modifier = Modifier.width(calcDp(percentage = 0.25f, dimension = CalcSizes.Dimension.Width))
        ) {

            // Display the reward value, or an empty string if reward is null
            Text(text = reward ?: "")
        }
    }
}