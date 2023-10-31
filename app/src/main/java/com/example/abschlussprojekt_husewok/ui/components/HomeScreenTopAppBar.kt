package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreenTopAppBar() {
    TopAppBar(
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
}