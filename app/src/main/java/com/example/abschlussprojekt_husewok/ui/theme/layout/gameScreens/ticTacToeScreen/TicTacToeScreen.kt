package com.example.abschlussprojekt_husewok.ui.theme.layout.gameScreens.ticTacToeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.NoBottomBarScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import dev.omkartenkale.explodable.Explodable
import dev.omkartenkale.explodable.rememberExplosionController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Composable function for the tic-tac-toe screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@Composable
fun TicTacToeScreen(navController: NavController, viewModel: MainViewModel) {
    // State to keep track of the player's turn
    var playerTurn by remember { mutableStateOf(true) }

    // State to keep track of the moves made by the players
    val moves = remember { mutableStateListOf<Boolean?>(null, null, null, null, null, null, null, null, null) }

    // State to keep track of the game result (won, lost, or draw)
    var gameWon by remember { mutableStateOf<Boolean?>(null) }

    // Get the current context
    val context = LocalContext.current

    // Create coroutine scopes for internet and main operations
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val mainScope = MainScope()

    // Create the explosion controller for the game end
    val explosionController = rememberExplosionController()

    // If it's the AI's turn and the game is not already won, perform AI move
    if (!playerTurn && gameWon == null) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                delay(500)

                // Find all available moves
                val freeIndices = moves
                    .mapIndexedNotNull { index, value -> if (value == null) index else null }

                // Check if the AI can win in the next move
                val winningMove = TicTacToeScreenFunctions.findAiMove(freeIndices, moves, false)
                if (winningMove != null) {
                    // Make the winning move
                    moves[winningMove] = false
                } else {
                    // Check if the player can win in the next move and block it
                    val blockingMove = TicTacToeScreenFunctions.findAiMove(freeIndices, moves, true)
                    if (blockingMove != null) {
                        // Make the blocking move
                        moves[blockingMove] = false
                    } else {
                        // No winning or blocking move, make a random move
                        moves[freeIndices.random()] = false
                    }
                }

                delay(500)

                // Check if the game is won
                gameWon = TicTacToeScreenFunctions.gameOverCheck(moves)

                // Finish the game if won, lost or draw
                if (gameWon != null) {
                    explosionController.explode()
                }

                // Switch to the Player's turn
                playerTurn = true
            }
        }
    }

    NoBottomBarScaffold(
        topBar = { NoNavigationTopAppBar() }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            val (header, explode, board, icons) = createRefs()

            // Display the player and turns
            TicTacToePlayer(
                playerTurn,
                Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    bottom.linkTo(explode.top)
                    centerHorizontallyTo(parent)
                }
            )

            Explodable(
                controller = explosionController,
                onExplode = {
                    mainScope.launch {
                        TicTacToeScreenFunctions.getNewHousework(
                            viewModel,
                            navController,
                            internetScope,
                            context,
                            gameWon as Boolean
                        )
                    }
                },
                modifier = Modifier.constrainAs(explode) {
                    centerTo(parent)
                }
            ) {
                // Display the tic tac toe board
                TicTacToeBoard(
                    modifier = Modifier.constrainAs(board) {
                        centerTo(parent)
                    }
                )

                // Display the tic tac toe icons
                TicTacToeIcons(
                    moves,
                    modifier = Modifier.constrainAs(icons) {
                        centerTo(board)
                    }
                ) { move ->
                    // Display the empty move
                    IconButton(
                        onClick = {
                            if (playerTurn && gameWon == null) {
                                // Make a move for the player
                                moves[move] = true

                                // Check if the game is won
                                gameWon = TicTacToeScreenFunctions.gameOverCheck(moves)

                                // Finish the game if won, lost or draw
                                if (gameWon != null) {
                                    explosionController.explode()
                                }

                                // Switch to the AI's turn
                                playerTurn = false
                            }
                        },
                        modifier = Modifier.fillMaxSize(1f)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_empty_circle),
                            contentDescription = null,
                            tint = Color.Transparent
                        )
                    }
                }
            }
        }
    }
}