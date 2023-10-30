package com.example.abschlussprojekt_husewok.ui.theme.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.exampledata.HouseworkData
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.components.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.components.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.components.HouseworkListCard
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import kotlinx.coroutines.launch

/**
 * Composable function to display the list screen.
 *
 * TODO: ViewModel
 * TODO: Add newTask Button onClick
 * TODO: Add HouseworkListCard onClick
 * TODO: Composable for sorting column
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun ListScreen() {
    // Create a state variable for the list of housework items
    var houseworkList by remember { mutableStateOf(HouseworkData.houseworkList) }

    // Create a state variable for the sorting method
    var sortedBy by remember { mutableStateOf(0) }

    // Create a nested scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create a lazy list state
    val lazyListState = rememberLazyListState()

    // Create a snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    // Create a coroutine scope
    val scope = rememberCoroutineScope()

    // Define the layout for the screen
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),

        // Display a topAppBar
        topBar = { BasicTopAppBar(scrollBehavior) },

        // Display a bottomAppBar
        bottomBar = { AnimatedBottomAppBar() },

        // Display a snackbar
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Purple40,
                    contentColor = Orange80,
                    shape = ShapeDefaults.ExtraSmall
                )
            }
        },

        // Display the content of the layout
        content = { innerPadding ->

            // Use a lazy column to display the list of housework items
            LazyColumn(
                contentPadding = PaddingValues(
                    calcDp(
                        percentage = 0.05f, dimension = Dimension.Height
                    )
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    calcDp(
                        percentage = 0.02f, dimension = Dimension.Height
                    )
                ),
                state = lazyListState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                // Display a header row with sorting options
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            calcDp(
                                percentage = 0.01f, dimension = Dimension.Height
                            )
                        ),
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Text(
                            text = "Sort by",
                            color = Color.White,
                            fontSize = calcSp(percentage = 0.05f),
                            fontWeight = FontWeight.Bold
                        )

                        // Display buttons for each sorting option
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(
                                calcDp(
                                    percentage = 0.8f, dimension = Dimension.Width
                                )
                            )
                        ) {

                            // Display a button for sorting by liked
                            Button(
                                shape = ShapeDefaults.ExtraSmall,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sortedBy == 1) Purple40 else Orange80,
                                    contentColor = if (sortedBy == 1) Orange80 else Purple40
                                ),
                                onClick = {
                                    sortedBy = 1
                                    houseworkList = houseworkList.sortedWith(
                                        compareBy({ !it.isLiked }, { it.title })
                                    )
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Sorted by liked")
                                    }
                                },
                                modifier = Modifier.width(
                                    calcDp(
                                        percentage = 0.25f, dimension = Dimension.Width
                                    )
                                )
                            ) {
                                Text(
                                    text = "Liked", fontSize = calcSp(percentage = 0.025f)
                                )
                            }

                            // Display a button for sorting by locked
                            Button(
                                shape = ShapeDefaults.ExtraSmall,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sortedBy == 2) Purple40 else Orange80,
                                    contentColor = if (sortedBy == 2) Orange80 else Purple40
                                ),
                                onClick = {
                                    sortedBy = 2
                                    houseworkList = houseworkList.sortedWith(
                                        compareBy({ !it.isLocked() }, { it.title })
                                    )
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Sorted by locked")
                                    }
                                },
                                modifier = Modifier.width(
                                    calcDp(
                                        percentage = 0.25f, dimension = Dimension.Width
                                    )
                                )
                            ) {
                                Text(
                                    text = "Locked", fontSize = calcSp(percentage = 0.025f)
                                )
                            }

                            // Display a button for sorting by random
                            Button(
                                shape = ShapeDefaults.ExtraSmall,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sortedBy == 3) Purple40 else Orange80,
                                    contentColor = if (sortedBy == 3) Orange80 else Purple40
                                ),
                                onClick = {
                                    sortedBy = 3
                                    houseworkList = houseworkList.shuffled()
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Sorted by random")
                                    }
                                },
                                modifier = Modifier.width(
                                    calcDp(
                                        percentage = 0.25f, dimension = Dimension.Width
                                    )
                                )
                            ) {
                                Text(
                                    text = "Random", fontSize = calcSp(percentage = 0.025f)
                                )
                            }
                        }
                    }
                }

                // Display a card for each housework item in the list
                items(houseworkList) { houseworkItem ->
                    HouseworkListCard(
                        housework = houseworkItem
                    )
                }

                // Display an add button at the end of the list
                item {
                    Button(shape = ShapeDefaults.ExtraSmall, colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40, contentColor = Orange80
                    ), modifier = Modifier
                        .width(
                            calcDp(
                                percentage = 0.8f, dimension = Dimension.Width
                            )
                        )
                        .height(
                            calcDp(
                                percentage = 0.05f, dimension = Dimension.Height
                            )
                        ), onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Add, contentDescription = null
                        )
                        Text(
                            text = "Add new task"
                        )
                    }
                }
            }
        })
}