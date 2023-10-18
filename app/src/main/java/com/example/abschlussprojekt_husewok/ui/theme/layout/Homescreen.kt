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
import androidx.compose.ui.tooling.preview.Preview
import com.example.abschlussprojekt_husewok.ui.components.BottomAppBar
import com.example.abschlussprojekt_husewok.ui.components.HomescreenCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Homescreen() {
    Scaffold(
        bottomBar = { BottomAppBar() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(it)
        ) {
            HomescreenCard()
        }
    }
}