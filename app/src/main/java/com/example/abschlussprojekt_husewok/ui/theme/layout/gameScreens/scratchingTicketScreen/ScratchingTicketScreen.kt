package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.scratchingTicketScreen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.cards.CardWithAnimatedBorder
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.NoBottomBarScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.MotionToasts
import com.jaber.scractch.CardScratch
import dev.omkartenkale.explodable.Explodable
import dev.omkartenkale.explodable.rememberExplosionController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Composable function that represents the screen for scratching a ticket in a lottery game.
 *
 * @param navController The navigation controller for navigating between screens.
 * @param viewModel The view model for accessing and updating data.
 */
@Composable
fun ScratchingTicketScreen(navController: NavController, viewModel: MainViewModel) {
    // List of phrases for the ticket
    val ticketPhrases = listOf(
        "Chore Champ!",
        "Lucky Break",
        "Easy Win",
        "You're a Star!",
        "Big Winner!"
    )

    // List of winning and losing words
    val winningWords = listOf(
        "Sparkling",
        "Effortless",
        "Tidy",
        "Organized",
        "Pristine",
        "Immaculate",
        "Spotless",
        "Fresh",
        "Neat",
        "Clean"
    )
    val losingWords = listOf(
        "Cluttered",
        "Messy",
        "Disorganized",
        "Dusty",
        "Grimy",
        "Untidy",
        "Stained",
        "Chaotic",
        "Neglected",
        "Dirty"
    )

    // State for the current ticket phrase and random word
    val ticketPhrase by remember { mutableStateOf(ticketPhrases.random()) }
    val randomWord by remember { mutableStateOf((winningWords + losingWords).random()) }

    // State for user confirmation
    var userConfirmation by remember { mutableStateOf("") }

    // Get the current context
    val context = LocalContext.current

    // Create coroutine scopes for internet and main operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val mainScope = MainScope()

    // Create the explosion controller for the game end
    val explosionController = rememberExplosionController()

    NoBottomBarScaffold(
        topBar = { NoNavigationTopAppBar() }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            Explodable(
                controller = explosionController,
                onExplode = {
                    mainScope.launch {
                        ScratchingTicketScreenFunctions.getNewHousework(
                            viewModel,
                            navController,
                            mainScope,
                            context,
                            winningWords.contains(randomWord)
                        )
                    }
                }
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.85f)) {
                    CardWithAnimatedBorder(
                        content = {
                            CardScratch(
                                cardBackgroundColor = Purple40,
                                scratchBoxColor = Purple80,
                                title = ticketPhrase,
                                titleTextColor = Color.White,
                                scratchText = randomWord
                            )
                        },
                        liked = true
                    )
                }
            }
            Column {
                WideTextField(
                    value = userConfirmation,
                    label = "Confirmation",
                    onValueChange = { value -> userConfirmation = value }
                )
                Spacer(modifier = Modifier.height(16.dp))
                WideButton(
                    text = "Check for a winner",
                    icon = Icons.Outlined.CheckCircle,
                    primary = true
                ) {
                    if (userConfirmation == randomWord) {
                        explosionController.explode()
                    } else {
                        MotionToasts.error(
                            "Please confirm your ticket",
                            "Can't check for winner",
                            context as Activity,
                            context
                        )
                    }
                }
            }
        }
    }
}