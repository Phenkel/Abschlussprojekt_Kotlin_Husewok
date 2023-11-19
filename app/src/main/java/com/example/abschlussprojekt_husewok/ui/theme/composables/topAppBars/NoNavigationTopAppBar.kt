package com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp

/**
 * A composable function that represents the top app bar without navigation icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoNavigationTopAppBar() {
    Column {
        // Display the center-aligned top app bar
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = OrangeGrey80,
                titleContentColor = Purple40,
                navigationIconContentColor = Purple40
            ),
            title = {
                Text(
                    text = "Hûséwøk",
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = calcSp(percentage = 0.075f)
                )
            }
        )

        // Display the divider with OrangeGrey80 color
        Divider(
            thickness = calcDp(percentage = 0.005f, dimension = CalcSizes.Dimension.Height),
            color = OrangeGrey80
        )

        // Display the divider with Color.White color
        Divider(
            thickness = calcDp(percentage = 0.005f, dimension = CalcSizes.Dimension.Height),
            color = Color.White
        )
    }
}