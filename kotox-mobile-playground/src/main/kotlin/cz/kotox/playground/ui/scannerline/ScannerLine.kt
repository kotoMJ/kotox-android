package cz.kotox.playground.ui.scannerline

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.kotox.core.ui.theme.KotoxBasicTheme

/*
 * Thanks to starter sample: https://gist.github.com/cyril-tl/0320b754a31b733897877c4fd6f0bd0e
 * https://stackoverflow.com/questions/76234960/cool-animated-line-over-an-image-with-jetpack-compose
 */
@Composable
fun ScannerLine(
    boxSize: Dp = 172.dp,
    verticalAnimationDuration: Int = 2000,
    color: Color = Color.Red,
    thickness: Dp = 2.dp,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val heightAnimation: Dp by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = boxSize,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = verticalAnimationDuration
                0.dp at 0 // ms
                0.dp at 0 + 300 // ms

//                80.dp at 300 with FastOutLinearInEasing
                boxSize at (verticalAnimationDuration / 2) - 150 with FastOutSlowInEasing
                boxSize at (verticalAnimationDuration / 2) + 150

                0.dp at verticalAnimationDuration - 300 with FastOutSlowInEasing
                0.dp at verticalAnimationDuration
            }
            // Use the default RepeatMode.Restart to start from 0.dp after each iteration
        )
    )

    val widthAnimation: Dp by infiniteTransition.animateValue(
        initialValue = 0.dp,
        targetValue = boxSize,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2 * verticalAnimationDuration
                0.dp at 0 //ms
                0.dp at 150 //ms
                0.dp at verticalAnimationDuration - 150
                150.dp at verticalAnimationDuration
                150.dp at verticalAnimationDuration*2 -150
                0.dp at verticalAnimationDuration*2
            }
        )
    )

    Box(
        modifier = Modifier
            .size(boxSize),
//            .border(1.dp, Color(0xFF000000), RoundedCornerShape(2.dp)),
    ) {
//            color = MaterialTheme.colorScheme.background // Set background color
//        Image(
//            painter = painterResource(id = R.drawable.datamatrix_jojo),
//            contentDescription = "start scanning",
//            modifier = Modifier.size(boxSize),
//        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.height(heightAnimation),
            )
            Divider(
                thickness = thickness,
                color = color,
                modifier = Modifier.width(widthAnimation)
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
fun ScannerLinePreview() {
    KotoxBasicTheme() {
        ScannerLine()
    }
}