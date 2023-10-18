package com.example.abschlussprojekt_husewok.ui.theme.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.components.BasicBottomAppBar
import com.example.abschlussprojekt_husewok.ui.components.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.components.HomescreenCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Homescreen() {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = { BasicTopAppBar() },
        bottomBar = { BasicBottomAppBar() },
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(it)
        ) {
            HomescreenCard(
                sizeHeight = calcDp(percentage = 0.7f, dimension = Dimension.Height)
            )
        }
    }
}