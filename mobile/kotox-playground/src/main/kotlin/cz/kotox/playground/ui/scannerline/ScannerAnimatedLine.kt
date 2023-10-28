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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
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

@Suppress("LongMethod", "LongParameterList")
@Composable
fun ScannerAnimatedLine(
    modifier: Modifier = Modifier,
    squareContentBoxSize: Dp = 172.dp,
    horizontalOverflowPeak: Dp = 104.dp,
    horizontalOverflow: Dp = 80.dp,
    animationVerticalDuration: Int = 2000,
    animationWaitingTimeOnTheEdgeInMillis: Int = 350,
    lineColor: Color = Color.Red,
    lineThickness: Dp = 2.dp,
    lineBlurEffectAlpha: Float = 0.25f,
    showDebugFrame: Boolean = false,
    squareBoxContent: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val halOfTheWaitingTimeOnTheEdgeInMillis = animationWaitingTimeOnTheEdgeInMillis / 2

    val lineMinHeightAnimationPx = 0.dp.dpToPx()
    val lineMaxHeightAnimationPx = squareContentBoxSize.dpToPx()
    val lineHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = lineMinHeightAnimationPx,
        targetValue = lineMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = heightKeyFramesSpec(
                animationVerticalDuration = animationVerticalDuration,
                minHeightAnimationPx = lineMinHeightAnimationPx,
                animationWaitingTimeOnTheEdgeInMillis = animationWaitingTimeOnTheEdgeInMillis,
                maxHeightAnimationPx = lineMaxHeightAnimationPx,
            )
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

    val lowerBlurMinHeightAnimationPx = 0.dp.dpToPx()
    val lowerBlurMaxHeightAnimationPx =
        squareContentBoxSize.dpToPx()
    val lowerBlurHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = lowerBlurMinHeightAnimationPx,
        targetValue = lowerBlurMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = (heightKeyFramesSpec(
                animationVerticalDuration = animationVerticalDuration,
                minHeightAnimationPx = lowerBlurMinHeightAnimationPx,
                animationWaitingTimeOnTheEdgeInMillis = animationWaitingTimeOnTheEdgeInMillis,
                maxHeightAnimationPx = lowerBlurMaxHeightAnimationPx,
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

    val lineWidthBumpEffectLengthInMillis = animationWaitingTimeOnTheEdgeInMillis - 10

    val lineMaxWidth: Dp = squareContentBoxSize.plus(horizontalOverflowPeak)

    val lineMaxWidthShrink: Dp = squareContentBoxSize.plus(horizontalOverflow)

    val widthAnimation: Dp by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = lineMaxWidthShrink,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2 * animationVerticalDuration
                0.dp at 0 //ms
                0.dp at animationVerticalDuration - 2 * halOfTheWaitingTimeOnTheEdgeInMillis

                lineMaxWidth at
                        animationVerticalDuration.minus(
                            lineWidthBumpEffectLengthInMillis / 2
                        ) with FastOutSlowInEasing

                lineMaxWidthShrink at
                        animationVerticalDuration.plus(
                            lineWidthBumpEffectLengthInMillis / 2
                        ) with FastOutSlowInEasing

                lineMaxWidthShrink at
                        animationVerticalDuration * 2 - halOfTheWaitingTimeOnTheEdgeInMillis
                0.dp at animationVerticalDuration * 2
            }
        )
    )

    val squareBoxSizeWithBlur: Dp = squareContentBoxSize

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
                .height(
                    lineHeightAnimationFloat
                        .toInt()
                        .pxToDp()
                )
                .width(widthAnimation)
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
                .height(
                    squareContentBoxSize - lineHeightAnimationFloat
                        .toInt()
                        .pxToDp()
                )
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
private fun ScannerAnimatedLinePreview() {
    KotoxBasicTheme() {

        var size by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.Center)
                    .background(color = Color.Yellow)
                    .onSizeChanged { size = it }
            ) {
                ScannerAnimatedLine(
                    modifier = Modifier,
                    showDebugFrame = true,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray),
                    )
                }
            }
        }
    }
}