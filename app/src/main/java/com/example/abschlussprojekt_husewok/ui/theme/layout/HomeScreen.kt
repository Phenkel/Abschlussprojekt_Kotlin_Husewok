package com.example.abschlussprojekt_husewok.ui.theme.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.components.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.components.HomeScreenTopAppBar
import com.example.abschlussprojekt_husewok.ui.components.HomescreenCard
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey

@Preview
@Composable
fun previewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}

/**
 * Composable function to display the home screen.
 *
 * TODO: ViewModel
 * TODO: Buttons onClick
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // Create a state variable for the active housework
    val activeHousework by remember {
        mutableStateOf(
            Housework(
                image = R.drawable.img_organize_paperwork,
                title = "Organize Paperwork",
                task1 = "Sort and organize documents",
                task2 = "File important papers",
                task3 = "Shred or discard unnecessary documents",
                lockDurationDays = 1
            )
        )
    }

    // Create a state variable for the available skip coins
    val skipCoins by remember {
        mutableIntStateOf(1)
    }

    // Define the layout for the screen
    Scaffold(containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),

        // Display a topAppBar
        topBar = { HomeScreenTopAppBar() },

        // Display a bottomAppBar
        bottomBar = { AnimatedBottomAppBar(navController, 0, true, false, false) },

        // Display the content of the layout
        content = { innerPadding ->

            // Use a Column to display the active housework
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {

                // Display a card to show the active housework
                HomescreenCard(skipButtonEnabled = skipCoins > 0, skipButtonOnClick = {
                    // TODO
                }, doneButtonOnClick = {
                    // TODO
                }, fabOnClick = {
                    // TODO
                }, iconButtonOnClick = {
                    // TODO
                }, housework = activeHousework
                )
            }
        })
}