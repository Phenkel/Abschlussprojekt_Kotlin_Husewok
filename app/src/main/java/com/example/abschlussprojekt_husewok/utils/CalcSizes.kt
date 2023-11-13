package com.example.abschlussprojekt_husewok.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * Helper object for calculating scaled pixels and density-independent pixels.
 */
object CalcSizes {
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
     * Calculates the desired size in scaled pixels (sp) based on a given percentage.
     *
     * @param percentage The percentage value to calculate the size from.
     * @return The calculated size in scaled pixels (sp).
     */
    @Composable
    fun calcSp(percentage: Float): TextUnit {
        // Get the density of the current display
        val density = LocalDensity.current.density

        // Get the density DPI of the current display
        val dpi = LocalContext.current.resources.displayMetrics.densityDpi.toFloat()

        // Calculate the desired size in scaled pixels (sp)
        return with(LocalDensity.current) {
            (percentage * density * dpi).toSp()
        }
    }

    /**
     * Calculates the desired size in density-independent pixels (dp) based on a given percentage and dimension.
     *
     * @param percentage The percentage value to calculate the size from.
     * @param dimension The dimension (Width, Height, or Full) to calculate the size for.
     * @return The calculated size in density-independent pixels (dp).
     */
    @Composable
    fun calcDp(percentage: Float, dimension: Dimension): Dp {
        // Get the screen dimensions
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val screenHeight = LocalConfiguration.current.screenHeightDp
        val screenFull = (screenWidth + screenHeight) / 2

        // Calculate the desired size in density-independent pixels (dp) based on the dimension
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
}