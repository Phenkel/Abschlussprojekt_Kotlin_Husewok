package com.example.abschlussprojekt_husewok.ui.theme.layout.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

@Composable
fun LockedStatus(housework: Housework) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = if (housework.isLocked() == true) "Locked:" else "Unlocked:",
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f)
        )
        Text(
            text = housework.lockExpirationDate,
            color = Color.White,
            fontSize = calcSp(percentage = 0.05f)
        )
    }
}