package cz.kotox.common.camera.custom.capture

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import cz.kotox.common.camera.custom.EMPTY_IMAGE_URI
import cz.kotox.common.camera.custom.LensFacing
import cz.kotox.common.camera.custom.R
import cz.kotox.common.camera.custom.capture.actionbutton.CaptureBackButton
import cz.kotox.common.camera.custom.capture.actionbutton.CaptureConfirmButton
import cz.kotox.common.camera.custom.capture.actionbutton.CapturePhotoLibraryButton
import cz.kotox.common.camera.custom.capture.layout.multi.CameraCaptureMultiLayout
import cz.kotox.common.camera.custom.gallery.GallerySelect
import cz.kotox.common.designsystem.preview.KotoxBasicThemeFullSizePreview
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.LocalColors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun CameraScreenMultiLayout(
    input: CameraScreenViewState,
    modifier: Modifier = Modifier,
    onEventHandler: (CameraScreenEvent) -> Unit = {}
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
    if (imageUri != EMPTY_IMAGE_URI) {
        Box(modifier = modifier) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Captured image"
            )

            CaptureBackButton(
                modifier = Modifier
                    .size(100.dp)
                    .align(if (isLandscape) Alignment.BottomEnd else Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                imageUri = EMPTY_IMAGE_URI
            }

            CaptureConfirmButton(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                    .align(if (isLandscape) Alignment.CenterEnd else Alignment.BottomCenter)
            ) {
                onEventHandler.invoke(CameraScreenEvent.ExitCamera(imageUri))
            }
        }
    } else {
        var showGallerySelect by remember { mutableStateOf(false) }
        if (showGallerySelect) {
            GallerySelect(
                onImageUri = { uri ->
                    showGallerySelect = false
                    imageUri = uri
                }
            )
        } else {
            Box(modifier = modifier) {
                CameraCaptureMultiLayout(
                    input = CameraCaptureInput(
                        currentSelector = input.currentCameraSelector,
                        currentZoomValues = input.currentZoomValues,
                        zoomStateObserver = input.zoomStateObserver
                    ),
                    modifier = modifier,
                    onEventHandler = { event ->
                        if (event is CameraScreenEvent.CaptureImageFile) {
                            imageUri = event.file?.toUri() ?: EMPTY_IMAGE_URI
                        }
                        onEventHandler.invoke(event)
                    }
                )
                CapturePhotoLibraryButton(
                    modifier = Modifier
                        .size(100.dp)
                        .align(if (isLandscape) Alignment.TopEnd else Alignment.BottomEnd)
                        .padding(24.dp)
                ) {
                    showGallerySelect = true
                }

                IconButton(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .defaultMinSize(32.dp)
                        .align(Alignment.TopStart),
                    onClick = {
                        onEventHandler.invoke(CameraScreenEvent.ExitCamera(EMPTY_IMAGE_URI))
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = LocalColors.current.divider // FIXME MJ, update proper color
                    )
                }
            }
        }
    }
}

@PreviewMobileLarge
@Composable
private fun CameraScreenContentPreview() {
    KotoxBasicThemeFullSizePreview {
        CameraScreenMultiLayout(
            input = CameraScreenViewState(LensFacing.BACK, currentZoomValues = null, Observer { }),
        )
    }
}
