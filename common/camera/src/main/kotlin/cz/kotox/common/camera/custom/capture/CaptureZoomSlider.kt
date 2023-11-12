package cz.kotox.common.camera.custom.capture

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import timber.log.Timber

data class CaptureZoomSliderInput(
    val zoomValues: ZoomValues,
    val showVertical: Boolean = false,
)

@SuppressLint("UnrememberedMutableState")//FIXME MJ
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun CaptureZoomSlider(
    @PreviewParameter(ZoomSliderPreviewProvider::class) input: CaptureZoomSliderInput,
    onValueChange: (value: Float) -> Unit = {}
) {

    val (timeLineSliderValueLocal, setTimeLineSliderValue) = remember { mutableStateOf(input.zoomValues.currentLinearZoom) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isInteracting by derivedStateOf { isPressed || isDragged }

    val sliderValue by derivedStateOf {
        if (isInteracting) {
            timeLineSliderValueLocal
        } else {
            input.zoomValues.currentLinearZoom
        }

    }

    if (input.showVertical) {
        /**
         * TODO vertical variant can be implemented simply by rotating this component modifier.rotate(-90f)
         * together with rotating the text.
         * There is just a bit issue with alignment of this component so vertical variant is omitted for now.
         */
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(visible = isInteracting) {
                Text(
                    text = "${input.zoomValues.currentRatio}",
                    color = Color.Black,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(percent = 50))
                        .background(Color.White)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp,
                        ),
                    fontFamily = FontFamily.Default,
                    fontSize = 12.sp,
                )
            }

            val thumbSize: Dp = if (isInteracting) 16.dp else 32.dp
            androidx.compose.material3.Slider(
                modifier = Modifier.semantics { contentDescription = "Localized Description" },
                value = sliderValue,
                colors = captureZoomSliderColors(),
                onValueChange = {
//            Timber.d(">>>_ seek in progress: $it")
                    setTimeLineSliderValue(it)
                    onValueChange(it)
                },
                onValueChangeFinished = {
                    Timber.d(">>>_ seek finished: ${timeLineSliderValueLocal}")
                    //onValueChangeFinished(timeLineSliderValueLocal.progress)
                },
                thumb = {
                    val shape = CircleShape
                    Spacer(
                        modifier = Modifier
                            .size(thumbSize)
                            .indication(
                                interactionSource = interactionSource,
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = thumbSize
                                )
                            )
                            .hoverable(interactionSource = interactionSource)
                            .shadow(if (/*enabled*/true) 6.dp else 0.dp, shape, clip = false)
                            .background(Color.White, shape)
                    )

                    AnimatedVisibility(visible = !isInteracting) {
                        Box(modifier = Modifier.defaultMinSize(minWidth = thumbSize, minHeight = thumbSize)) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center,
                                text = "${input.zoomValues.currentRatio}",
                                color = Color.Black,
                                fontFamily = FontFamily.Default,
                                fontSize = 12.sp,
                            )
                        }
                    }
                },
                interactionSource = interactionSource,
            )
        }
    }
}

@Composable
private fun captureZoomSliderColors(): androidx.compose.material3.SliderColors = androidx.compose.material3.SliderDefaults.colors(
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent,
    inactiveTrackColor = Color(0xFF1d1d1d),
    activeTrackColor = Color(0xFF7d7d7d),
    thumbColor = Color.White
)

class ZoomSliderPreviewProvider : PreviewParameterProvider<CaptureZoomSliderInput> {
    override val values: Sequence<CaptureZoomSliderInput> = sequenceOf(
        CaptureZoomSliderInput(
            zoomValues = ZoomValues(
                minRatio = 0.7f,
                defaultRatio = 1f,
                maxRatio = 7f,
                currentRatio = 1f,
                currentLinearZoom = 0.36446497f
            ),
            showVertical = false
        )
    )
}
