package com.example.abschlussprojekt_husewok.ui.theme.layout.onboardingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.example.abschlussprojekt_husewok.ui.theme.composables.cards.CardWithAnimatedBorder
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.OnlyContentScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.cards.HouseworkListCard
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.ssjetpackcomposeswipeableview.SwipeAbleItemView
import com.example.ssjetpackcomposeswipeableview.SwipeDirection

/**
 * A composable function that represents the onboarding screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing and updating data.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnboardingScreen(navController: NavController, viewModel: MainViewModel) {
    OnlyContentScaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp)
            ) {
                // Display the onboarding text
                Text(
                    text = "You can swipe cards\nto get more info",
                    color = Color.White,
                    fontSize = CalcSizes.calcSp(percentage = 0.05f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // Display the swipeable item view
                SwipeAbleItemView(
                    leftViewIcons = arrayListOf(
                        Triple(
                            painterResource(id = R.drawable.ic_house),
                            Purple80,
                            "Go"
                        )
                    ),
                    rightViewIcons = arrayListOf(
                        Triple(
                            painterResource(id = R.drawable.ic_house),
                            Purple80,
                            "Go"
                        )
                    ),
                    onClick = { navController.navigate("home") },
                    swipeDirection = SwipeDirection.LEFT,
                    leftViewBackgroundColor = Orange40,
                    rightViewBackgroundColor = Purple40,
                    position = 1,
                    leftViewWidth = CalcSizes.calcDp(
                        percentage = 0.2f,
                        dimension = CalcSizes.Dimension.Width
                    ),
                    rightViewWidth = CalcSizes.calcDp(
                        percentage = 0.2f,
                        dimension = CalcSizes.Dimension.Width
                    ),
                    height = CalcSizes.calcDp(
                        percentage = 0.15f,
                        dimension = CalcSizes.Dimension.Height
                    ),
                    cornerRadius = 20.dp,
                    leftSpace = (-CalcSizes.calcDp(
                        percentage = 0.075f,
                        dimension = CalcSizes.Dimension.Width
                    )),
                    rightSpace = (-CalcSizes.calcDp(
                        percentage = 0.075f,
                        dimension = CalcSizes.Dimension.Width
                    ))
                ) {
                    // Display the card with animated border
                    CardWithAnimatedBorder(
                        content = {
                            HouseworkListCard(
                                housework = Housework(
                                    image = R.drawable.play_store_512,
                                    title = "Swipe me to start",
                                    task1 = "",
                                    task2 = "",
                                    task3 = "",
                                    isLiked = true,
                                    lockDurationDays = 7,
                                    lockExpirationDate = "",
                                    default = true,
                                    id = ""
                                )
                            )
                        },
                        liked = true
                    )
                }
            }
        }
    }
}