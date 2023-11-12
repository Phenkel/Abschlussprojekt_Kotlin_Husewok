package com.example.abschlussprojekt_husewok.ui.theme.components.editables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.abschlussprojekt_husewok.utils.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.popovanton0.heartswitch.HeartSwitch
import com.popovanton0.heartswitch.HeartSwitchColors

/**
 * Composable function to display a switch for liked and disliked options.
 *
 * @param liked The current value indicating whether it is liked.
 * @param onCheckedChange The callback function to handle the switch state change.
 */
@Composable
fun LikedDislikedSwitch(liked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {

        // Display the text indicating liked or disliked
        Text(
            text = if (liked) "Liked" else "Disliked",
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f)
        )

        // Display the HeartSwitch with specified colors and state
        HeartSwitch(
            checked = liked,
            onCheckedChange = onCheckedChange,
            colors = HeartSwitchColors(
                checkedTrackColor = Purple40,
                uncheckedTrackColor = Orange40,
                checkedTrackBorderColor = Purple80,
                uncheckedTrackBorderColor = Orange80,
                checkedThumbColor = Purple80,
                uncheckedThumbColor = Orange80
            )
        )
    }
}