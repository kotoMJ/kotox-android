package cz.kotox.camera.custom.capture

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.kotox.android.camera.custom.R
import cz.kotox.core.ui.theme.KotoxBasicTheme

@Composable
fun CaptureBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val contentPadding = PaddingValues(if (isPressed) 8.dp else 12.dp)

    KotoxBasicTheme() {
        OutlinedButton(
            modifier = modifier,
            shape = CircleShape,
            border = BorderStroke(2.dp, Color.White),
            contentPadding = contentPadding,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                backgroundColor = Color.Black
            ),
            onClick = { /*GNDN*/ },
            enabled = false
        ) {

            IconButton(onClick = { onClick.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_undo),
                    contentDescription = null,
                    tint = Color.White
                )

            }

        }
    }
}

@Preview
@Composable
fun PreviewCaptureBackButton() {
    Scaffold(
        modifier = Modifier
            .size(125.dp)
            .wrapContentSize()
    ) { innerPadding ->
        CaptureBackButton(
            modifier = Modifier
                .padding(innerPadding)
                .size(32.dp)
        )
    }
}
