package com.example.abschlussprojekt_husewok.ui.theme.layout.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.RewardRow
import com.example.abschlussprojekt_husewok.ui.theme.composables.statics.UserInfoRow
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.Constants.auth

/**
 * Composable function for the profile screen.
 *
 * @param navController The NavController for navigating between screens.
 * @param viewModel The MainViewModel instance for accessing data and business logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {
    // Get the current user state from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    // Define the scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create the basic scaffold with top and bottom app bars
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "home") },
        bottomBar = { AnimatedBottomAppBar(navController, 2, false, false, true) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(calcDp(0.02f, CalcSizes.Dimension.Height)),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Add vertical spacing with calculated height
            Spacer(modifier = Modifier.height(calcDp(0.02f, CalcSizes.Dimension.Height)))

            // Display the housework image
            HouseworkImage(image = R.drawable.play_store_512)

            // Display user information rows
            UserInfoRow("UserID:", user?.userId?.substring(0,10) + "...")
            UserInfoRow("Tasks Done/Skipped:", "${user?.tasksDone} / ${user?.tasksSkipped}")
            UserInfoRow("Games Won/Lost:", "${user?.gamesWon} / ${user?.gamesLost}")
            UserInfoRow("Skip Coins:", user?.skipCoins.toString())

            // Display the user's reward row if available
            user?.reward?.let { reward ->
                RewardRow(reward) {
                    viewModel.updateUserReward()
                }
            }

            // Display the logout button
            WideButton(text = "Logout", icon = Icons.Outlined.ExitToApp, primary = false) {
                // Sign out the user, clear data, and navigate to the login screen
                auth.signOut()
                viewModel.logoutClearData()
                navController.navigate("login")
            }
        }
    }
}