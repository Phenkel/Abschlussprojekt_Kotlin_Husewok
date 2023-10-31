package com.example.abschlussprojekt_husewok.ui.theme.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.components.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.components.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey

/**
 * Composable function to display the profile screen.
 *
 * TODO: ViewModel
 * TODO: Add change Reward Button onClick
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Profilescreen() {
    val user by remember {
        mutableStateOf("HlNsuks4O4bn8TzcbzY63Q6pC092")
    }
    val tasksDone by remember {
        mutableIntStateOf(14)
    }
    val tasksSkipped by remember {
        mutableIntStateOf(3)
    }
    val gamesWon by remember {
        mutableIntStateOf(3)
    }
    val gamesLost by remember {
        mutableIntStateOf(14)
    }
    val skipCoins by remember {
        mutableIntStateOf(14)
    }
    var reward by remember {
        mutableStateOf("Joke")
    }

    // Define the layout of the screen
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),

        // Display a topAppBar
        topBar = { BasicTopAppBar() },

        // Display a bottomAppBar
        bottomBar = { AnimatedBottomAppBar() },

        content = { innerPadding ->

            // Use a column to show all profile information
            Column(
                verticalArrangement = Arrangement.spacedBy(calcDp(percentage = 0.02f, dimension = Dimension.Height)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {

                // Display a image to show the logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(calcDp(percentage = 0.8f, dimension = Dimension.Width))
                        .height(calcDp(percentage = 0.333f, dimension = Dimension.Height))
                )

                // Use a row to show the user id
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "UserID:",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                    Text(
                        text = user,
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                }

                // Use a row to show the tasks done and skipped amount
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Tasks Done/Skipped:",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                    Text(
                        text = "$tasksDone / $tasksSkipped",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                }

                // Use a row to show the games won and lost amount
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Games Won/Lost:",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                    Text(
                        text = "$gamesWon / $gamesLost",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                }

                // Use a row to show the skip coins amount
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Skip Coins:",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                    Text(
                        text = skipCoins.toString(),
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )
                }

                // Use a row to let user choose the reward
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Reward:",
                        fontSize = calcSp(percentage = 0.03f),
                        color = Color.White
                    )

                    // Display a button to let the user choose the reward
                    Button(
                        shape = ShapeDefaults.ExtraSmall,
                        onClick = {
                            reward = if (reward == "Joke") {
                                "Bored"
                            } else {
                                "Joke"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (reward == "Joke") Purple40 else Orange80,
                            contentColor = if (reward == "Joke") Orange80 else Purple40
                        ),
                        modifier = Modifier
                            .width(calcDp(percentage = 0.25f, dimension = Dimension.Width))
                    ) {
                        Text(text = reward)
                    }
                }
            }
        }
    )
}