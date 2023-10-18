package com.example.abschlussprojekt_husewok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.ui.components.HomescreenCard
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.abschlussprojekt_husewok.ui.components.HouseworkListCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val showDialog = remember { mutableStateOf(false) }
            val activeHousework = Housework(
                image = R.drawable.clean_floors,
                title = "Clean Floors",
                description = "Pick everything up from floors\nVacuum floors\nMop floors",
                lockDurationDays = 7
            )
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text(text = activeHousework.title) },
                    text = { Text(text = activeHousework.description) },
                    confirmButton = {
                        Button(
                            onClick = { showDialog.value = false },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Got it")
                        }
                    },
                    properties = DialogProperties(dismissOnClickOutside = true)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.background),
                        contentScale = ContentScale.Crop,
                        alpha = 0.3f
                    )
                    .fillMaxSize(1f)
            ) {
                /*
                HomescreenCard(
                    housework = activeHousework,
                    fabOnClick = {
                        showDialog.value = true
                    }
                )
                 */
                HomescreenCard(housework = activeHousework)
            }
        }
    }
}