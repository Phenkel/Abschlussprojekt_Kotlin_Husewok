package com.example.abschlussprojekt_husewok.ui.theme.components.scaffolds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey

/**
 * A composable function that creates a basic scaffold with a transparent container,
 * background image, top bar, bottom bar, and content.
 *
 * @param topBar The composable function that represents the top bar.
 * @param bottomBar The composable function that represents the bottom bar.
 * @param content The composable function that represents the content of the scaffold.
 */
@Composable
fun BasicScaffold(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .background(color = backgroundGrey)
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop,
                alpha = 0.333f
            ),
        topBar = topBar,
        bottomBar = bottomBar,
        content = content
    )
}