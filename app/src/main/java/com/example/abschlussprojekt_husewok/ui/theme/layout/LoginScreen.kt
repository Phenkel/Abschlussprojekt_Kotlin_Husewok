package com.example.abschlussprojekt_husewok.ui.theme.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.Orange80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey

@Preview
@Composable
fun previewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),

        content = { innerPadding ->

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
            ) {
                val (image, button, text) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            centerHorizontallyTo(parent)
                        }
                        .size(calcDp(percentage = 0.8f, dimension = Dimension.Width))
                )

                Button(shape = ShapeDefaults.ExtraSmall, colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40, contentColor = Orange80
                ), modifier = Modifier
                    .constrainAs(button) {
                        centerTo(parent)
                    }
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
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.1f))
                    Text(
                        text = "Login with Google"
                    )
                }

                Text(
                    text = "Made by Phenkel",
                    color = Color.White,
                    fontSize = calcSp(percentage = 0.05f),
                    modifier = Modifier
                        .constrainAs(text){
                            bottom.linkTo(parent.bottom)
                            centerHorizontallyTo(parent)
                        }
                )
            }
        })
}