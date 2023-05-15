package cz.kotox.playground.ui.scannerline

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    squareBoxContent: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val halOfTheWaitingTimeOnTheEdgeInMillis = animationWaitingTimeOnTheEdgeInMillis / 2

    val squareBoxSizePx = squareBoxSize.dpToPx()
    val heightAnimation: Float by infiniteTransition.animateFloat(
        initialValue = 0.dp.dpToPx(),
        targetValue = squareBoxSize.dpToPx(),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = animationVerticalDuration
                0f at 0 // ms
                0f at 0 + animationWaitingTimeOnTheEdgeInMillis // ms

                squareBoxSizePx at (animationVerticalDuration / 2) - halOfTheWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                squareBoxSizePx at (animationVerticalDuration / 2) + halOfTheWaitingTimeOnTheEdgeInMillis

                0f at animationVerticalDuration - animationWaitingTimeOnTheEdgeInMillis with FastOutSlowInEasing
                0f at animationVerticalDuration
            }
            // Use the default RepeatMode.Restart to start from 0.dp after each iteration
        )
    )


    val animationMaxWidth: Dp =
        squareBoxSize * (PERCENT_HUNDRED + squareBoxLineOversizeInPercent) / PERCENT_HUNDRED

    val widthAnimation: Dp by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = animationMaxWidth,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2 * animationVerticalDuration
                0.dp at 0 //ms
                0.dp at halOfTheWaitingTimeOnTheEdgeInMillis //ms
                0.dp at animationVerticalDuration - halOfTheWaitingTimeOnTheEdgeInMillis
                animationMaxWidth at animationVerticalDuration
                animationMaxWidth at animationVerticalDuration * 2 - halOfTheWaitingTimeOnTheEdgeInMillis
                0.dp at animationVerticalDuration * 2
            }
        )
    )

    /**
     * DEV NOTE: place the graphic to be behind the line here.
     */
    Box(
        modifier = modifier
            .size(animationMaxWidth, height = squareBoxSize)
        //.border(1.dp, Color.Gray, RoundedCornerShape(2.dp)),

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
            Divider(
                thickness = lineThickness,
                color = lineColor,
                modifier = Modifier
                    .width(widthAnimation)
                    .graphicsLayer(translationY = heightAnimation)
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