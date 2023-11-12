package com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.utils.Dimension
import com.example.abschlussprojekt_husewok.utils.calcDp
import com.example.abschlussprojekt_husewok.utils.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80

/**
 * Composable function to display the basic top app bar.
 *
 * @param scrollBehavior The scroll behavior for the top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
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
            },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )

        // Display the dividers
        Divider(
            thickness = calcDp(percentage = 0.005f, dimension = Dimension.Height),
            color = OrangeGrey80
        )
        Divider(
            thickness = calcDp(percentage = 0.005f, dimension = Dimension.Height),
            color = Color.White
        )
    }
}