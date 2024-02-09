package cz.kotox.common.camera.custom.capture.actionbutton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cz.kotox.common.camera.custom.R
import cz.kotox.common.designsystem.preview.PreviewMobileLarge
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicThemeWidgetPreview

@Composable
fun CaptureConfirmButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val contentPadding = PaddingValues(if (isPressed) 8.dp else 12.dp)
    OutlinedButton(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.White),
        contentPadding = contentPadding,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.White,
            backgroundColor = Color.Black
        ),
        onClick = { /* GNDN */ },
        enabled = false
    ) {
        Button(
            modifier = Modifier
                .fillMaxSize(),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            ),
            interactionSource = interactionSource,
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check_bold),
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

@PreviewMobileLarge
@Composable
internal fun CaptureConfirmButtonPreview(){
    KotoxBasicThemeWidgetPreview {
        CaptureConfirmButton(
            modifier = Modifier
                .size(100.dp)
        )
    }
}
