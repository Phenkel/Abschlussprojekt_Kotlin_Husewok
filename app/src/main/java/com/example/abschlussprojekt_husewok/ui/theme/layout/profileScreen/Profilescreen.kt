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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.components.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.theme.components.statics.HouseworkImage
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.Constants.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A composable function that represents the profile screen of the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The MainViewModel used for accessing user data and performing actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: MainViewModel) {
    // Collect the current user state from the view model
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    // Define the scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Create a coroutine scope for Firebase operations
    val firebaseScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Compose the profile screen UI using BasicScaffold
    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "home") },
        bottomBar = { AnimatedBottomAppBar(navController, 2, false, false, true) }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(
                calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            Spacer(
                modifier = Modifier.height(
                    calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)
                )
            )
            // Display the housework image
            HouseworkImage(image = R.drawable.play_store_512)

            // Display the user information rows
            UserInfoRow("UserID:", user?.userId)
            UserInfoRow("Tasks Done/Skipped:", "${user?.tasksDone} / ${user?.tasksSkipped}")
            UserInfoRow("Games Won/Lost:", "${user?.gamesWon} / ${user?.gamesLost}")
            UserInfoRow("Skip Coins:", user?.skipCoins.toString())

            // Display the user reward row if available
            user?.reward?.let {
                RewardRow(it) {
                    // Update the user's reward when clicked
                    firebaseScope.launch {
                        viewModel.updateUserReward()
                    }
                }
            }

            // Display the logout button
            WideButton(text = "Logout", icon = Icons.Outlined.ExitToApp, primary = false) {
                // Perform logout actions and navigate to the login screen
                auth.signOut()
                navController.navigate("login")
            }
        }
    }
}