package cz.kotox.common.camera.custom.capture.zoom

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.kotox.common.camera.custom.LensFacing
import cz.kotox.common.camera.custom.capture.ZoomValues
import cz.kotox.common.designsystem.preview.KotoxBasicThemeWidgetPreview
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.KotoxBasicTheme

@OptIn(ExperimentalFoundationApi::class)
@SuppressWarnings("MagicNumber")
@Composable
fun CaptureZoomToggle(
    input: CaptureZoomToggleViewState,
    onChange: (ratio: Float) -> Unit = {},
    onLongPress: () -> Unit = {},
) {

    val darkColor = Color(0xFF1d1d1d)//FIXME MJ
    val contrastColor = Color.White

    Surface(
        shape = RoundedCornerShape(percent = 50),
        elevation = 4.dp,
        modifier = input.modifier
            .wrapContentSize()
    ) {
        if (input.showVertical) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(percent = 50))
                    .background(color = darkColor)
            ) {

                input.zoomValues.toToggleStates(input.lensFacing).forEach { floatValue ->
                    Text(
                        text = "$floatValue",
                        color = if (floatValue == input.zoomValues.currentRatio) {
                            darkColor
                        } else {
                            contrastColor
                        },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(percent = 50))
                            .combinedClickable(
                                onClick = {
                                    onChange.invoke(floatValue)
                                },
                                onLongClick = { onLongPress.invoke() }
                            )
                            .background(
                                if (floatValue == input.zoomValues.currentRatio) {
                                    contrastColor
                                } else {
                                    darkColor
                                }
                            )
                            .padding(
                                vertical = 16.dp,
                                horizontal = 8.dp,
                            ),
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(percent = 50))
                    .background(color = darkColor)
            ) {
                input.zoomValues.toToggleStates(input.lensFacing).forEach { floatValue ->
                    Text(
                        text = "$floatValue",
                        color = if (floatValue == input.zoomValues.currentRatio) {
                            darkColor
                        } else {
                            contrastColor
                        },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(percent = 50))
                            .combinedClickable(
                                onClick = {
                                    onChange.invoke(floatValue)
                                },
                                onLongClick = { onLongPress.invoke() }
                            )
                            .background(
                                if (floatValue == input.zoomValues.currentRatio) {
                                    contrastColor
                                } else {
                                    darkColor
                                }
                            )
                            .padding(
                                vertical = 8.dp,
                                horizontal = 16.dp,
                            ),
                    )
                }
            }
        }
    }
}

data class CaptureZoomToggleViewState(
    val modifier: Modifier = Modifier,
    val zoomValues: ZoomValues,
    val lensFacing: LensFacing?,
    val showVertical: Boolean = false,
)

val horizontal: CaptureZoomToggleViewState = CaptureZoomToggleViewState(
    zoomValues = ZoomValues(
        minRatio = 0.7f,
        defaultRatio = 1f,
        maxRatio = 7f,
        currentRatio = 1f,
        currentLinearZoom = 0.36446497f
    ),
    lensFacing = LensFacing.BACK,
    showVertical = false
)

@PreviewMobileLarge
@Composable
internal fun CaptureZoomToggleHorizontalPreview() {
    KotoxBasicThemeWidgetPreview {
        CaptureZoomToggle(
            input = horizontal
        )
    }
}

val verticalBack: CaptureZoomToggleViewState = CaptureZoomToggleViewState(
    zoomValues = ZoomValues(
        minRatio = 0.7f,
        defaultRatio = 1f,
        maxRatio = 7f,
        currentRatio = 1.1f,
        currentLinearZoom = 0.36446497f
    ),
    lensFacing = LensFacing.BACK,
    showVertical = true
)

@PreviewMobileLarge
@Composable
internal fun CaptureZoomToggleVerticalBackPreview() {
    KotoxBasicThemeWidgetPreview {
        CaptureZoomToggle(
            input = verticalBack
        )
    }
}

val verticalFront: CaptureZoomToggleViewState = CaptureZoomToggleViewState(
    zoomValues = ZoomValues(
        minRatio = 1f,
        defaultRatio = 1f,
        maxRatio = 10f,
        currentRatio = 1.2f,
        currentLinearZoom = 0.36446497f
    ),
    lensFacing = LensFacing.FRONT,
    showVertical = true
)

@PreviewMobileLarge
@Composable
internal fun CaptureZoomToggleVerticalFrontPreview() {
    KotoxBasicThemeWidgetPreview {
        CaptureZoomToggle(
            input = verticalFront
        )
    }
}

