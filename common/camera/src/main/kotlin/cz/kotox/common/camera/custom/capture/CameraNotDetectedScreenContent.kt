package cz.kotox.common.camera.custom.capture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import cz.kotox.common.camera.custom.R
import cz.kotox.common.designsystem.preview.KotoxBasicThemeFullSizePreview
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.LocalColors

@Composable
internal fun CameraNotDetectedScreenContent(backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.camera_capture_camera_not_detected),
            color = LocalColors.current.textNorm,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewMobileLarge
@Composable
internal fun CameraNotDetectedScreenContentPreview() {
    KotoxBasicThemeFullSizePreview {
        CameraNotDetectedScreenContent(
            backgroundColor = LocalColors.current.background
        )
    }
}