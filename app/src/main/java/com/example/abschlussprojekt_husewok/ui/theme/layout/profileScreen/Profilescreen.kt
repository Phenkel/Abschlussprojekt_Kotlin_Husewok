package com.example.abschlussprojekt_husewok.ui.theme.layout.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.utils.Dimension
import com.example.abschlussprojekt_husewok.utils.calcDp
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.components.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel

/**
 * Composable function to display the profile screen.
 *
 * @param navController The NavController used for navigation.
 * @param viewModel The MainViewModel used for data access.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the current user state from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    // Define scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        topBar = { BasicTopAppBar(scrollBehavior) },
        bottomBar = { AnimatedBottomAppBar(navController, 2, false, false, true) },
        content = { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    calcDp(percentage = 0.02f, dimension = Dimension.Height)
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                Spacer(
                    modifier = Modifier.height(
                        calcDp(percentage = 0.02f, dimension = Dimension.Height)
                    )
                )

                // Display the logo image
                HouseworkImage(image = R.drawable.play_store_512)

                // Display user information rows
                UserInfoRow("UserID:", user?.userId)
                UserInfoRow("Tasks Done/Skipped:", "${user?.tasksDone} / ${user?.tasksSkipped}")
                UserInfoRow("Games Won/Lost:", "${user?.gamesWon} / ${user?.gamesLost}")
                UserInfoRow("Skip Coins:", user?.skipCoins.toString())

                // Display the reward row with a button to update the reward
                RewardRow(user?.reward) {
                    viewModel.updateUserReward()
                }
            }
        }
    )
}