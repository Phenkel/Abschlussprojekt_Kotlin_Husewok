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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnboardingScreen(navController: NavController, viewModel: MainViewModel) {
    // Composable function for the onboarding screen
    OnlyContentScaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Card dimensions and spacing
            val cardWidthPercentage = 0.2f
            val cardHeightPercentage = 0.15f
            val cardWidth = CalcSizes.calcDp(cardWidthPercentage, CalcSizes.Dimension.Width)
            val cardHeight = CalcSizes.calcDp(cardHeightPercentage, CalcSizes.Dimension.Height)
            val cardLeftSpace = -CalcSizes.calcDp(0.075f, CalcSizes.Dimension.Width)
            val cardRightSpace = -CalcSizes.calcDp(0.075f, CalcSizes.Dimension.Width)

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                // Text displaying instructions
                Text(
                    text = "You can swipe cards\nto get more info",
                    color = Color.White,
                    fontSize = CalcSizes.calcSp(0.05f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // Swipeable card view
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
                    leftViewWidth = cardWidth,
                    rightViewWidth = cardWidth,
                    height = cardHeight,
                    cornerRadius = 20.dp,
                    leftSpace = cardLeftSpace,
                    rightSpace = cardRightSpace
                ) {
                    // Card content
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