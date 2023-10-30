package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40
import com.example.abschlussprojekt_husewok.ui.theme.Purple80
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.items.wigglebutton.WiggleButton

// TODO: Navigation
@Preview
@Composable
fun AnimatedBottomAppBar() {
    var selected by remember {
        mutableStateOf(0)
    }
    var home by remember {
        mutableStateOf(true)
    }
    var list by remember {
        mutableStateOf(false)
    }
    var profile by remember {
        mutableStateOf(false)
    }

    AnimatedNavigationBar(
        selectedIndex = selected,
        barColor = OrangeGrey80,
        ballColor = Purple80,
    ) {
        Box(
            modifier = Modifier.size(calcDp(percentage = 0.06f, dimension = Dimension.Height)),
            contentAlignment = Alignment.BottomCenter
        ) {
            WiggleButton(
                isSelected = home,
                onClick = {
                    selected = 0
                    home = true
                    list = false
                    profile = false
                },
                icon = R.drawable.ic_house,
                backgroundIcon = R.drawable.ic_circle,
                backgroundIconColor = Purple80,
                outlineColor = Orange40,
                wiggleColor = Purple40,
                modifier = Modifier.fillMaxSize(0.9f)
            )
        }
        Box(
            modifier = Modifier.size(calcDp(percentage = 0.06f, dimension = Dimension.Height)),
            contentAlignment = Alignment.BottomCenter
        ) {
            WiggleButton(
                isSelected = list,
                onClick = {
                    selected = 1
                    home = false
                    list = true
                    profile = false
                },
                icon = R.drawable.ic_list,
                backgroundIcon = R.drawable.ic_circle,
                backgroundIconColor = Purple80,
                outlineColor = Orange40,
                wiggleColor = Purple40,
                modifier = Modifier.fillMaxSize(0.9f)
            )
        }
        Box(
            modifier = Modifier.size(calcDp(percentage = 0.06f, dimension = Dimension.Height)),
            contentAlignment = Alignment.BottomCenter
        ) {
            WiggleButton(
                isSelected = profile,
                onClick = {
                    selected = 2
                    home = false
                    list = false
                    profile = true
                },
                icon = R.drawable.ic_profile,
                backgroundIcon = R.drawable.ic_circle,
                backgroundIconColor = Purple80,
                outlineColor = Orange40,
                wiggleColor = Purple40,
                modifier = Modifier.fillMaxSize(0.9f)
            )
        }
    }
}