package com.example.abschlussprojekt_husewok.ui.calc

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * Enum class representing the dimensions (Width or Height) used for calculating sizes.
 */
enum class Dimension {
    /**
     * Represents the Width dimension.
     */
    Width,

    /**
     * Represents the Height dimension.
     */
    Height,

    /**
     * Represents the Full screen.
     */
    Full
}

/**
 * Calculates the desired size in sp (scaled pixels) based on a given percentage.
 *
 * @param percentage The percentage value to calculate the size from.
 * @return The calculated size in sp (scaled pixels).
 * @author Philipp Henkel
 */
@Composable
fun calcSp(percentage: Float): TextUnit {
    val dpi = LocalContext.current.resources.displayMetrics.densityDpi.toFloat()
    return with(LocalDensity.current) {
        (percentage * density * dpi).toSp()
    }
}

/**
 * Calculates the desired size in density-independent pixels (dp) based on a given percentage and dimension.
 *
 * @param percentage The percentage value to calculate the size from.
 * @param dimension The dimension (Width, Height or Full) to calculate the size for.
 * @return The calculated size in density-independent pixels (dp).
 * @author Philipp Henkel
 */
@Composable
fun calcDp(percentage: Float, dimension: Dimension): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenFull = (screenWidth + screenHeight) / 2
    return when (dimension) {
        Dimension.Width -> {
            with(LocalDensity.current) {
                (percentage * density * screenWidth).toDp()
            }
        }
        Dimension.Height -> {
            with(LocalDensity.current) {
                (percentage * density * screenHeight).toDp()
            }
        }
        Dimension.Full -> {
            with(LocalDensity.current) {
                (percentage * density * screenFull).toDp()
            }
        }
    }
}