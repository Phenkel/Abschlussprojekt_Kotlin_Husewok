package com.example.abschlussprojekt_husewok.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.ui.calc.Dimension
import com.example.abschlussprojekt_husewok.ui.calc.calcDp
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.dislikedColors
import com.example.abschlussprojekt_husewok.ui.theme.likedColors
import java.util.Collections.rotate

@Preview
@Composable
fun previewCardWithAnimatedBorder() {
    CardWithAnimatedBorder(content = { HouseworkListCard() }, liked = true)
}

/**
 * TODO: Documentation
 *
 * @param
 * @author Benjamin Klimpel
 */
@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    liked: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush =
        if (liked) Brush.sweepGradient(likedColors)
        else Brush.sweepGradient(dislikedColors)

    Surface(modifier = modifier, shape = RoundedCornerShape(20.dp)) {
        Surface(
            modifier =
            Modifier
                .clipToBounds()
                .padding(calcDp(percentage = 0.01f, dimension = Dimension.Full))
                .drawWithContent {
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = Color.Transparent,
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundGrey)
            ) { content() }
        }
    }
}