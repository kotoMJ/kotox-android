package cz.kotox.playground.ui.bouncingbox

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Suppress("All JetPack Compose previews contain 'Preview' in method name")
@Composable
fun BouncigBox(density: Density = LocalDensity.current) {
    val instaColors = listOf(Color.Yellow, Color.Red, Color.Magenta)
    var visible by remember { mutableStateOf(false) }
    val translateYState = animateFloatAsState(
        targetValue = if (visible) 0f else -700f,
        animationSpec = spring(0.7f, stiffness = 100f),
        label = ""
    )
    Column() {
        Column(modifier = Modifier.size(300.dp)) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Canvas(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(translationY = translateYState.value)
                ) {
                    drawRoundRect(
                        brush = SolidColor(Color.LightGray),
                        size = Size(this.size.width, 1000f),
                        topLeft = Offset(0f, -300f),
                        style = Fill
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { visible = !visible }) {
                Text("Visible")
            }
        }
    }
}