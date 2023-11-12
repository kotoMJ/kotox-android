package cz.kotox.common.camera.custom.capture

import cz.kotox.common.camera.custom.LensFacing
import timber.log.Timber

private const val DEFAULT_FRONT_OPTIMAL_MAX_RATIO_VALUE = 1.4f

private const val DEFAULT_BACK_OPTIMAL_MAX_RATIO_VALUE = 2f

data class ZoomValues(
    val minRatio: Float,
    val defaultRatio: Float = 1f,
    val maxRatio: Float,
    val currentRatio: Float,
    val currentLinearZoom: Float
) {
    fun toToggleStates(lensFacing: LensFacing?): List<Float> {
        val defaultOptimalMaxRatioValue = if (lensFacing == LensFacing.BACK) {
            DEFAULT_BACK_OPTIMAL_MAX_RATIO_VALUE
        } else {
            DEFAULT_FRONT_OPTIMAL_MAX_RATIO_VALUE
        }
        val optimalMaxRatio = kotlin.math.min(defaultOptimalMaxRatioValue, maxRatio)

        val toggleSet = if (minRatio < defaultRatio) {
            if (currentRatio < defaultRatio) {
                listOf(currentRatio, defaultRatio, optimalMaxRatio)
            } else if (currentRatio == defaultRatio) {
                listOf(minRatio, currentRatio, optimalMaxRatio)
            } else if (currentRatio > defaultRatio) {
                listOf(minRatio, defaultRatio, currentRatio)
            } else {
                listOf(minRatio, defaultRatio, optimalMaxRatio)
            }
        } else {
            if (currentRatio == minRatio) {
                listOf(minRatio, optimalMaxRatio)
            } else {
                if (currentRatio < optimalMaxRatio) {
                    listOf(currentRatio, optimalMaxRatio)
                } else {
                    listOf(minRatio, currentRatio)
                }
            }
        }

        Timber.d(">>>_ ZOOM toggles: $toggleSet from values: $this")
        return toggleSet
    }
}
