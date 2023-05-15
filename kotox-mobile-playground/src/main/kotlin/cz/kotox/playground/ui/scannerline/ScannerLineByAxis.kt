package cz.kotox.playground.ui.scannerline

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.kotox.core.ui.theme.KotoxBasicTheme

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

private const val PERCENT_HUNDRED = 100

@Suppress("LongMethod", "LongParameterList")
@Composable
fun ScannerLineByAxis(
    modifier: Modifier = Modifier,
    animationVerticalDuration: Int = 2000,
    animationWaitingTimeOnTheEdgeInMillis: Int = 300,
    lineColor: Color = Color.Red,
    lineThickness: Dp = 2.dp,
    squareContentBoxSize: Dp = 172.dp,
    lineOversizeTheSquareContentInPercent: Int = 39,
    lineBlurEffectHeight: Dp = 80.dp,
    lineBlurEffectAlpha: Float = 0.25f,
    lineWidthBumpEffectDelta: Dp = 32.dp,
    showDebugFrame: Boolean = false,
    squareBoxContent: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val halOfTheWaitingTimeOnTheEdgeInMillis = animationWaitingTimeOnTheEdgeInMillis / 2

    val lineMinHeightAnimationPx = 0.dp.dpToPx() + lineBlurEffectHeight.dpToPx()
    val lineMaxHeightAnimationPx = squareContentBoxSize.dpToPx() + lineBlurEffectHeight.dpToPx()
    val lineHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = lineMinHeightAnimationPx,
        targetValue = lineMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = heightKeyFramesSpec(
                animationVerticalDuration,
                lineMinHeightAnimationPx,
                animationWaitingTimeOnTheEdgeInMillis,
                lineMaxHeightAnimationPx,
            )
        )
    )

    val upperBlurMinHeightAnimationPx = 0.dp.dpToPx()
    val upperBlurMaxHeightAnimationPx = squareContentBoxSize.dpToPx()
    val upperBlurHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = upperBlurMinHeightAnimationPx,
        targetValue = upperBlurMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = (heightKeyFramesSpec(
                animationVerticalDuration,
                upperBlurMinHeightAnimationPx,
                animationWaitingTimeOnTheEdgeInMillis,
                upperBlurMaxHeightAnimationPx,
            ))
        )
    )
    val upperBlurAlphaFloat: Float by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationVerticalDuration
                0f at 0 //ms
                0f at 0 + animationWaitingTimeOnTheEdgeInMillis //ms
                1f at (animationVerticalDuration / 2) - halOfTheWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                0f at (animationVerticalDuration / 2) + halOfTheWaitingTimeOnTheEdgeInMillis
                0f at animationVerticalDuration
            }
        )
    )

    val lowerBlurMinHeightAnimationPx = 0.dp.dpToPx() + lineBlurEffectHeight.dpToPx()
    val lowerBlurMaxHeightAnimationPx =
        squareContentBoxSize.dpToPx() + lineBlurEffectHeight.dpToPx()
    val lowerBlurHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = lowerBlurMinHeightAnimationPx,
        targetValue = lowerBlurMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = (heightKeyFramesSpec(
                animationVerticalDuration,
                lowerBlurMinHeightAnimationPx,
                animationWaitingTimeOnTheEdgeInMillis,
                lowerBlurMaxHeightAnimationPx,
            ))
        )
    )
    val lowerBlurAlphaFloat: Float by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationVerticalDuration
                0f at 0 //ms
                0f at (animationVerticalDuration / 2) + (halOfTheWaitingTimeOnTheEdgeInMillis)
                1f at (animationVerticalDuration) - (animationWaitingTimeOnTheEdgeInMillis) with FastOutSlowInEasing
                0f at animationVerticalDuration with FastOutSlowInEasing
            }
        )
    )

    val lineWidthBumpEffectLengthInMillis = animationWaitingTimeOnTheEdgeInMillis - 50
    val lineMaxWidth: Dp =
        (squareContentBoxSize * (PERCENT_HUNDRED + lineOversizeTheSquareContentInPercent) / PERCENT_HUNDRED) +
                lineWidthBumpEffectDelta

    val widthAnimation: Dp by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = lineMaxWidth - 8.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2 * animationVerticalDuration
                0.dp at 0 //ms
                0.dp at halOfTheWaitingTimeOnTheEdgeInMillis //ms
                0.dp at animationVerticalDuration - halOfTheWaitingTimeOnTheEdgeInMillis
                lineMaxWidth at
                        animationVerticalDuration.minus(lineWidthBumpEffectLengthInMillis / 2) with
                        FastOutSlowInEasing
                lineMaxWidth.minus(lineWidthBumpEffectDelta) at animationVerticalDuration.plus(
                    lineWidthBumpEffectLengthInMillis / 2
                ) with FastOutSlowInEasing
                lineMaxWidth.minus(lineWidthBumpEffectDelta) at
                        animationVerticalDuration * 2 - halOfTheWaitingTimeOnTheEdgeInMillis
                0.dp at animationVerticalDuration * 2
            }
        )
    )

    val squareBoxSizeWithBlur: Dp = squareContentBoxSize.plus(lineBlurEffectHeight.times(2))

    Box(
        modifier = modifier
            .size(width = lineMaxWidth, height = squareBoxSizeWithBlur)
            .conditional(showDebugFrame) {
                border(1.dp, Color.Gray, RoundedCornerShape(2.dp))
            }
    ) {
        Box(
            modifier = Modifier
                .size(squareContentBoxSize)
                .align(Alignment.Center),
            content = squareBoxContent
        )

        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier
                .height(lineBlurEffectHeight)
                .width(widthAnimation)
                .graphicsLayer(translationY = upperBlurHeightAnimationFloat)
                .drawWithCache {
                    onDrawBehind {
                        val brush = Brush.verticalGradient(
                            0f to lineColor.copy(alpha = 0f),
                            1f to lineColor.copy(alpha = lineBlurEffectAlpha)
                        )
                        drawRect(
                            brush = brush,
                            alpha = upperBlurAlphaFloat
                        )
                    }

                }
            )
        }
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier
                .height(lineBlurEffectHeight)
                .width(widthAnimation)
                .graphicsLayer(translationY = lowerBlurHeightAnimationFloat)
                .drawWithCache {
                    onDrawBehind {
                        val brush = Brush.verticalGradient(
                            0f to lineColor.copy(alpha = lineBlurEffectAlpha),
                            1f to lineColor.copy(alpha = 0f)
                        )
                        drawRect(
                            brush = brush,
                            alpha = lowerBlurAlphaFloat
                        )
                    }

                }
            )
        }
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                thickness = lineThickness,
                color = lineColor,
                modifier = Modifier
                    .width(widthAnimation)
                    .graphicsLayer(translationY = lineHeightAnimationFloat)
            )
        }
    }
}

@Composable
private fun heightKeyFramesSpec(
    animationVerticalDuration: Int,
    minHeightAnimationPx: Float,
    animationWaitingTimeOnTheEdgeInMillis: Int,
    maxHeightAnimationPx: Float,
) = keyframes {
    durationMillis = animationVerticalDuration
    minHeightAnimationPx at 0 // ms
    minHeightAnimationPx at 0 + animationWaitingTimeOnTheEdgeInMillis // ms

    maxHeightAnimationPx at
            (animationVerticalDuration / 2) - (animationWaitingTimeOnTheEdgeInMillis / 2) with FastOutSlowInEasing
    maxHeightAnimationPx at (animationVerticalDuration / 2) + (animationWaitingTimeOnTheEdgeInMillis / 2)

    minHeightAnimationPx at
            animationVerticalDuration - animationWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
    minHeightAnimationPx at animationVerticalDuration
}


@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun ScannerLineByAxisPreview() {
    KotoxBasicTheme() {
        ScannerLineByAxis(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray),
            )
        }
    }
}