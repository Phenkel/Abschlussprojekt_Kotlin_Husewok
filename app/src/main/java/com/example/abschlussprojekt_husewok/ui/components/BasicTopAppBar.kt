package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.calc.calcSp
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BasicTopAppBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = OrangeGrey80,
            titleContentColor = Color.Transparent
        ),
        title = {
            Text(
                text = "Hûséwøk",
                color = Purple40,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = calcSp(percentage = 0.075f)
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .height(calcDp(percentage = 0.075f, dimension = Dimension.Height))
                    .width(calcDp(percentage = 0.2f, dimension = Dimension.Width)),
                contentScale = ContentScale.Fit
            )
        }
    )
}