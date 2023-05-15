package cz.kotox.playground.ui.scannerline

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
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

private const val PERCENT_HUNDRED = 100

@Composable
fun ScannerLineByAxis(
    modifier: Modifier = Modifier,
    animationVerticalDuration: Int = 2000,
    animationWaitingTimeOnTheEdgeInMillis: Int = 300,
    lineColor: Color = Color.Red,
    lineThickness: Dp = 2.dp,
    squareBoxSize: Dp = 172.dp,
    squareBoxLineOversizeInPercent: Int = 39,
    squareBoxBlurHeight: Dp = 24.dp,
    squareBoxContent: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val halOfTheWaitingTimeOnTheEdgeInMillis = animationWaitingTimeOnTheEdgeInMillis / 2

    val lineMinHeightAnimationPx = 0.dp.dpToPx() + squareBoxBlurHeight.dpToPx()
    val lineMaxHeightAnimationPx = squareBoxSize.dpToPx()+ squareBoxBlurHeight.dpToPx()
    val lineHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = lineMinHeightAnimationPx,
        targetValue = lineMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationVerticalDuration
                lineMinHeightAnimationPx at 0 // ms
                lineMinHeightAnimationPx at 0 + animationWaitingTimeOnTheEdgeInMillis // ms

                lineMaxHeightAnimationPx at
                        (animationVerticalDuration / 2) - halOfTheWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                lineMaxHeightAnimationPx at (animationVerticalDuration / 2) + halOfTheWaitingTimeOnTheEdgeInMillis

                lineMinHeightAnimationPx at
                        animationVerticalDuration - animationWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                lineMinHeightAnimationPx at animationVerticalDuration
            }
            // Use the default RepeatMode.Restart to start from 0.dp after each iteration
        )
    )

    val upperBlurMinHeightAnimationPx = 0.dp.dpToPx()
    val upperBlurMaxHeightAnimationPx = squareBoxSize.dpToPx()
    val upperBlurHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = upperBlurMinHeightAnimationPx,
        targetValue = upperBlurMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationVerticalDuration
                upperBlurMinHeightAnimationPx at 0 // ms
                upperBlurMinHeightAnimationPx at 0 + animationWaitingTimeOnTheEdgeInMillis // ms

                upperBlurMaxHeightAnimationPx at
                        (animationVerticalDuration / 2) - halOfTheWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                upperBlurMaxHeightAnimationPx at (animationVerticalDuration / 2) + halOfTheWaitingTimeOnTheEdgeInMillis

                upperBlurMinHeightAnimationPx at
                        animationVerticalDuration - animationWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                upperBlurMinHeightAnimationPx at animationVerticalDuration
            }
            // Use the default RepeatMode.Restart to start from 0.dp after each iteration
        )
    )

    val lowerBlurMinHeightAnimationPx = 0.dp.dpToPx() + squareBoxBlurHeight.dpToPx()
    val lowerBlurMaxHeightAnimationPx = squareBoxSize.dpToPx() + squareBoxBlurHeight.dpToPx()
    val lowerBlurHeightAnimationFloat: Float by infiniteTransition.animateFloat(
        initialValue = lowerBlurMinHeightAnimationPx,
        targetValue = lowerBlurMaxHeightAnimationPx,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationVerticalDuration
                lowerBlurMinHeightAnimationPx at 0 // ms
                lowerBlurMinHeightAnimationPx at 0 + animationWaitingTimeOnTheEdgeInMillis // ms

                lowerBlurMaxHeightAnimationPx at
                        (animationVerticalDuration / 2) - halOfTheWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                lowerBlurMaxHeightAnimationPx at (animationVerticalDuration / 2) + halOfTheWaitingTimeOnTheEdgeInMillis

                lowerBlurMinHeightAnimationPx at
                        animationVerticalDuration - animationWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                lowerBlurMinHeightAnimationPx at animationVerticalDuration
            }
            // Use the default RepeatMode.Restart to start from 0.dp after each iteration
        )
    )


    val lineMaxWidth: Dp =
        squareBoxSize * (PERCENT_HUNDRED + squareBoxLineOversizeInPercent) / PERCENT_HUNDRED

    val widthAnimation: Dp by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = lineMaxWidth,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2 * animationVerticalDuration
                0.dp at 0 //ms
                0.dp at halOfTheWaitingTimeOnTheEdgeInMillis //ms
                0.dp at animationVerticalDuration - halOfTheWaitingTimeOnTheEdgeInMillis
                lineMaxWidth at animationVerticalDuration
                lineMaxWidth at animationVerticalDuration * 2 - halOfTheWaitingTimeOnTheEdgeInMillis
                0.dp at animationVerticalDuration * 2
            }
        )
    )

    val squareBoxSizeWithBlur: Dp = squareBoxSize.plus(squareBoxBlurHeight.times(2))
    /**
     * DEV NOTE: place the graphic to be behind the line here.
     */
    Box(
        modifier = modifier
            .size(width = lineMaxWidth, height = squareBoxSizeWithBlur)
            .border(1.dp, Color.Gray, RoundedCornerShape(2.dp)),

        ) {
        Box(
            modifier = Modifier
                .size(squareBoxSize)
                .align(Alignment.Center),
            content = squareBoxContent
        )

        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier
                .height(24.dp)
                .width(widthAnimation)
                .graphicsLayer(translationY = upperBlurHeightAnimationFloat)
                .drawWithCache {
                    onDrawBehind {
                        val brush = Brush.verticalGradient(
                            0f to lineColor.copy(alpha = 0f),
                            1f to lineColor.copy(alpha = 0.45f)
                        )
                        drawRect(
                            //size = Size(width = widthAnimation.toPx(), height = 24.dp.toPx()),
                            brush = brush,
                            alpha = if (widthAnimation.value == 0f) 0.0f else 0.45f
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
                .height(24.dp)
                .width(widthAnimation)
                .graphicsLayer(translationY = lowerBlurHeightAnimationFloat)
                .drawWithCache {
                    onDrawBehind {
                        val brush = Brush.verticalGradient(
                            0f to lineColor.copy(alpha = 0.45f),
                            1f to lineColor.copy(alpha = 0f)
                        )
                        drawRect(
                            //size = Size(width = widthAnimation.toPx(), height = 24.dp.toPx()),
                            brush = brush,
                            alpha = if (widthAnimation.value == 0f) 0.0f else 0.45f
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


@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun ScannerLineByAxisPreview() {
    KotoxBasicTheme() {
        ScannerLineBySpacer()
    }
}