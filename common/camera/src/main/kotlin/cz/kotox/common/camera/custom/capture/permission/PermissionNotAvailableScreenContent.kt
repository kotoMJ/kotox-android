package cz.kotox.common.camera.custom.capture.permission

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.kotox.common.camera.custom.R
import cz.kotox.common.camera.custom.utils.AppSettingsIntentUtils
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicThemeFullSizePreview
import cz.kotox.common.designsystem.theme.shiraz.LocalColors

@Composable
internal fun PermissionNotAvailableScreenContent(backgroundColor: Color, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.camera_capture_permission_not_available),
                    color = LocalColors.current.textNorm,
                    textAlign = TextAlign.Center
                )
                OutlinedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    border = BorderStroke(2.dp, LocalColors.current.textNorm),
                    onClick = {
                        context.startActivity(AppSettingsIntentUtils.getAppSettingsIntent(context))
                    }
                ) {
                    Text(text = "Permission settings", color = LocalColors.current.textNorm)
                }
            }
        }
    }
}

@PreviewMobileLarge
@Composable
internal fun PermissionNotAvailableScreenContentPreview() {
    KotoxBasicThemeFullSizePreview {
        PermissionNotAvailableScreenContent(
            backgroundColor = LocalColors.current.background,
            LocalContext.current
        )
    }
}
