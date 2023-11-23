package com.example.abschlussprojekt_husewok.ui.theme.layout.chatbotScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.composables.bottomAppBars.AnimatedBottomAppBar
import com.example.abschlussprojekt_husewok.ui.theme.composables.buttons.WideButton
import com.example.abschlussprojekt_husewok.ui.theme.composables.editables.WideTextField
import com.example.abschlussprojekt_husewok.ui.theme.composables.scaffolds.BasicScaffold
import com.example.abschlussprojekt_husewok.ui.theme.composables.topAppBars.BasicTopAppBar
import com.example.abschlussprojekt_husewok.ui.viewModel.MainViewModel
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcSp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

val test = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In dictum nulla sit amet condimentum consequat. Sed gravida auctor enim, quis vestibulum enim convallis a. Nullam vulputate sapien sit amet est aliquet aliquam. Nulla facilisi. Pellentesque vitae velit eu ipsum varius iaculis. Donec in urna vitae dolor aliquet tincidunt. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Morbi eleifend enim a laoreet commodo. Nam consectetur lacinia ligula, non lacinia purus fermentum id. Nunc rhoncus lorem et est laoreet, ut consectetur velit laoreet. Ut non elit cursus, feugiat nibh non, egestas dui. Donec id orci vel nisl congue lobortis. Sed sodales orci vitae neque suscipit, ut dignissim erat placerat. Curabitur auctor sodales commodo. Proin luctus, justo et vestibulum iaculis, leo nisi bibendum orci, ac aliquam mauris lectus ut elit. Integer mattis varius tellus, a eleifend massa gravida eget."

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(navController: NavController, viewModel: MainViewModel) {
    val palmResponse by viewModel.palmOutput.collectAsStateWithLifecycle()

    var chatRequest by remember { mutableStateOf("") }

    // Set up scroll behavior for the top app bar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val scrollState = rememberScrollState()

    val margin = calcDp(percentage = 0.02f, dimension = CalcSizes.Dimension.Height)

    // Set up coroutine scopes for internet operations and UI updates
    val internetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val mainScope = MainScope()

    BasicScaffold(
        topBar = { BasicTopAppBar(scrollBehavior, navController, "home") },
        bottomBar = { AnimatedBottomAppBar(navController, 3, false, false, false, true) }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(innerPadding)
        ) {
            val (instruction, textField, button, answer) = createRefs()

            Text(
                text = "Need help with a specific task?\n\n" +
                        "Just ask and I try to help\n" +
                        "with some instructions",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = calcSp(percentage = 0.045f),
                modifier = Modifier.constrainAs(instruction) {
                    top.linkTo(parent.top, margin)
                    centerHorizontallyTo(parent)
                }
            )

            WideTextField(
                value = chatRequest,
                label = "Task",
                onValueChange = { value -> chatRequest = value },
                modifier = Modifier.constrainAs(textField) {
                    top.linkTo(instruction.bottom, margin)
                    centerHorizontallyTo(parent)
                }
            )

            WideButton(
                text = "Ask for help",
                icon = Icons.Outlined.Face,
                primary = true,
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(textField.bottom, margin)
                    centerHorizontallyTo(parent)
                }
            ) {
                if (chatRequest.isNotEmpty()) {
                    internetScope.launch {
                        viewModel.generateText(chatRequest)
                    }
                }
            }

            if (palmResponse.isNotEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(calcDp(percentage = 0.4f, dimension = CalcSizes.Dimension.Height))
                        .background(Purple40.copy(alpha = 0.5f), shape = RoundedCornerShape(20.dp))
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                        .constrainAs(answer) {
                            top.linkTo(button.bottom)
                            bottom.linkTo(parent.bottom)
                            centerHorizontallyTo(parent)
                        }
                ) {
                    Text(
                        text = palmResponse,
                        color = Color.White,
                        fontSize = calcSp(percentage = 0.03f)
                    )
                }
            }
        }
    }
}