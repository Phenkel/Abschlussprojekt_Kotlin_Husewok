package com.example.abschlussprojekt_husewok.ui.theme.composables.editables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.esatgozcu.rollingnumber.RollingNumberVM
import com.esatgozcu.rollingnumber.RollingNumberView
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents the locked duration in days editor.
 *
 * @param lockDurationDays The current locked duration in days.
 * @param addOnClick The callback function to invoke when the add button is clicked.
 * @param removeOnClick The callback function to invoke when the remove button is clicked.
 */
@Composable
fun LockedDurationDaysEdit(
    lockDurationDays: Long,
    addOnClick: () -> Unit,
    removeOnClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        // Display the "Locked Days" text
        Text(
            text = "Locked Days",
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Display the remove button
            IconButton(
                onClick = removeOnClick,
                enabled = lockDurationDays > 1
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove_circle),
                    contentDescription = null,
                    tint = Orange80
                )
            }

            // Display the rolling number view
            RollingNumberView(
                vm = RollingNumberVM(
                    number = String.format("%02d", lockDurationDays),
                    textStyle = TextStyle(
                        fontSize = calcSp(percentage = 0.05f),
                        color = Color.White
                    )
                )
            )

            // Display the add button
            IconButton(
                onClick = addOnClick,
                enabled = lockDurationDays < 14
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = null,
                    tint = Purple80
                )
            }
        }
    }
}