package com.example.abschlussprojekt_husewok.ui.theme.layout.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.utils.Dimension
import com.example.abschlussprojekt_husewok.utils.calcDp
import com.example.abschlussprojekt_husewok.ui.theme.components.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.cards.CardWithAnimatedBorder
import com.example.abschlussprojekt_husewok.ui.theme.components.topAppBars.NoNavigationTopAppBar
import com.example.abschlussprojekt_husewok.ui.theme.components.HomescreenCard
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val activeHousework by viewModel.activeHousework.collectAsStateWithLifecycle()
    var loading by remember {
        mutableStateOf(false)
    }
    var progress by remember {
        mutableFloatStateOf(0f)
    }
    if (activeHousework == null) {
        LaunchedEffect("loadHousework") {
            loading = true
            for (i in 1..100) {
                if (i == 33) viewModel.updateHouseworkList()
                if (i == 66) viewModel.getActiveHousework()
                if (activeHousework != null) loading = false
                progress = i.toFloat() / 100
                delay(30)
            }
            loading = false
        }
    } else {
        loading = false
    }
    Scaffold(containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        topBar = { NoNavigationTopAppBar() },
        bottomBar = { AnimatedBottomAppBar(navController, 0, true, false, false) },
        content = { innerPadding ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                activeHousework?.let {
                    CardWithAnimatedBorder(
                        content = {
                            HomescreenCard(skipButtonEnabled = (user?.skipCoins ?: 0) > 0,
                                skipButtonOnClick = {
                                    viewModel.updateActiveHousework(true)
                                    viewModel.updateUserSkipCoins(false)
                                }, doneButtonOnClick = {
                                    viewModel.updateActiveHousework(true)
                                    viewModel.updateUserSkipCoins(true)
                                }, fabOnClick = {
                                    // TODO
                                }, iconButtonOnClick = {
                                    if (it.title != "All done") {
                                        viewModel.upsertHouseworkFirebase(Housework(
                                            image = it.image,
                                            title = it.title,
                                            task1 = it.task1,
                                            task2 = it.task2,
                                            task3 = it.task3,
                                            isLiked = !it.isLiked,
                                            lockDurationDays = it.lockDurationDays,
                                            lockExpirationDate = it.lockExpirationDate,
                                            default = it.default,
                                            id = it.id
                                        ))
                                    }
                                }, housework = it
                            )
                        }, liked = it.isLiked
                    )
                }
            }

            if (loading) {
                ConstraintLayout(
                    modifier = Modifier
                        .height(calcDp(percentage = 1f, dimension = Dimension.Height))
                        .width(calcDp(percentage = 1f, dimension = Dimension.Width))
                ) {
                    val (indicator) = createRefs()

                    CircularProgressIndicator(
                        progress = progress,
                        color = Orange80,
                        modifier = Modifier
                            .width(calcDp(percentage = 0.25f, dimension = Dimension.Full))
                            .height(calcDp(percentage = 0.25f, dimension = Dimension.Height))
                            .constrainAs(indicator) {
                                centerTo(parent)
                            }
                    )
                }
            }
        })
}