package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.colorGuessScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.SmallButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.NoBottomBarScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import dev.omkartenkale.explodable.Explodable
import dev.omkartenkale.explodable.rememberExplosionController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Composable function that represents the color guessing screen.
 *
 * @param navController The NavController for handling navigation.
 * @param viewModel The MainViewModel for accessing data and performing operations.
 */
@Composable
fun ColorGuessScreen(navController: NavController, viewModel: MainViewModel) {
    val colors = listOf(Orange80, Purple80)
    // Random Primary Color
    val primary by remember { mutableStateOf(colors.random()) }
    // Secondary color matching the primary
    val secondary by remember {
        mutableStateOf(
            if (primary == Orange80) Orange40
            else Purple40
        )
    }
    // Color chosen by the user
    var chosenColor by remember { mutableStateOf<Color?>(null) }

    // Get the flip controller
    val flipController = rememberFlipController()

    // Get the explosion controller
    val explosionController = rememberExplosionController()

    // Create a scope for the delay
    val scope = rememberCoroutineScope()

    // Get the current context
    val context = LocalContext.current

    // Create coroutine scopes for internet and main operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val mainScope = MainScope()

    // Compose the UI
    NoBottomBarScaffold(
        topBar = { NoNavigationTopAppBar() }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            // Flippable card
            Flippable(
                frontSide = { CardToGuess() },
                backSide = {
                    Explodable(
                        controller = explosionController,
                        onExplode = {
                            mainScope.launch {
                                ColorGuessScreenFunctions.getNewHousework(
                                    viewModel,
                                    navController,
                                    internetScope,
                                    context,
                                    chosenColor == primary
                                )
                            }
                        }
                    ) {
                        GuessedCard(primary, secondary)
                    }
                },
                flipController = flipController,
                flipOnTouch = false,
                flipDurationMs = 1000,
                onFlippedListener = {
                    scope.launch {
                        delay(1000)
                        explosionController.explode()
                    }
                }
            )

            // Button Row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                SmallButton(
                    text = "Orange",
                    primary = false
                ) {
                    if (chosenColor == null) {
                        flipController.flip()
                        chosenColor = Orange80
                    }
                }
                SmallButton(
                    text = "Purple",
                    primary = true
                ) {
                    if (chosenColor == null) {
                        flipController.flip()
                        chosenColor = Purple80
                    }
                }
            }
        }
    }
}