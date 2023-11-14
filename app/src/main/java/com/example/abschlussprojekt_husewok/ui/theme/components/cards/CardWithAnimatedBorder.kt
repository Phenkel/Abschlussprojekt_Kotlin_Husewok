package com.example.abschlussprojekt_husewok.ui.theme.components.cards

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.abschlussprojekt_husewok.ui.theme.backgroundGrey
import com.example.abschlussprojekt_husewok.ui.theme.dislikedColors
import com.example.abschlussprojekt_husewok.ui.theme.likedColors
import com.example.abschlussprojekt_husewok.utils.CalcSizes
import com.example.abschlussprojekt_husewok.utils.CalcSizes.calcDp

/**
 * A composable function that represents a card with an animated border.
 *
 * @param modifier The modifier for the card.
 * @param content The content to display within the card.
 * @param liked Whether the card is liked or not.
 */
@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    liked: Boolean
) {
    // Create an infinite transition for the rotation animation
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Create a brush for the border gradient based on the liked status
    val brush = if (liked) Brush.sweepGradient(likedColors) else Brush.sweepGradient(dislikedColors)

    Surface(modifier = modifier, shape = RoundedCornerShape(20.dp)) {
        Surface(
            modifier = Modifier
                .clipToBounds()
                .padding(calcDp(percentage = 0.01f, dimension = CalcSizes.Dimension.Full))
                .drawWithContent {
                    // Rotate the content with the animated angle
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                    drawContent()
                },
            color = Color.Transparent,
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier.background(backgroundGrey)
            ) {
                content()
            }
        }
    }
}