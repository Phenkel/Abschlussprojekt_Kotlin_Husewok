package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.theme.Orange40
import com.example.abschlussprojekt_husewok.ui.theme.OrangeGrey80
import com.example.abschlussprojekt_husewok.ui.theme.Purple40

enum class BottomIcons {
    HOME,
    LIST,
    PROFILE
}

@Preview
@Composable
fun BasicBottomAppBar() {
    val selected = remember {
        mutableStateOf(BottomIcons.HOME)
    }

    androidx.compose.material3.BottomAppBar(
        containerColor = OrangeGrey80,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                IconButton(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(calcDp(percentage = 0.3f, dimension = Dimension.Width)),
                    onClick = { selected.value = BottomIcons.HOME }
                ) {
                    Icon(
                        imageVector = if (selected.value == BottomIcons.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = null,
                        tint = if (selected.value == BottomIcons.HOME) Purple40 else Orange40,
                        modifier = Modifier.fillMaxSize(if (selected.value == BottomIcons.HOME) 0.5f else 0.3f)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(calcDp(percentage = 0.3f, dimension = Dimension.Width)),
                    onClick = { selected.value = BottomIcons.LIST }
                ) {
                    Icon(
                        imageVector = if (selected.value == BottomIcons.LIST) Icons.Filled.List else Icons.Outlined.List,
                        contentDescription = null,
                        tint = if (selected.value == BottomIcons.LIST) Purple40 else Orange40,
                        modifier = Modifier.fillMaxSize(if (selected.value == BottomIcons.LIST) 0.5f else 0.3f)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(calcDp(percentage = 0.3f, dimension = Dimension.Width)),
                    onClick = { selected.value = BottomIcons.PROFILE }
                ) {
                    Icon(
                        imageVector = if (selected.value == BottomIcons.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                        contentDescription = null,
                        tint = if (selected.value == BottomIcons.PROFILE) Purple40 else Orange40,
                        modifier = Modifier.fillMaxSize(if (selected.value == BottomIcons.PROFILE) 0.5f else 0.3f)
                    )
                }
            }
        })
}