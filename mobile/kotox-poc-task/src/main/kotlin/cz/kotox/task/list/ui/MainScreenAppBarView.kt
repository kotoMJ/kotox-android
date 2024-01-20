package cz.kotox.task.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cz.kotox.common.designsystem.theme.LocalColors
import cz.kotox.common.designsystem.theme.LocalTypography
import cz.kotox.common.designsystem.theme.KotoxBasicTheme


sealed class MeetingNoteAppBarEvent {
    object AppBarHomeEvent : MeetingNoteAppBarEvent()
    data class BackButtonOffset(val offset: Offset, val size: IntSize) : MeetingNoteAppBarEvent()
}

data class MainScreenAppBarInput constructor(
    val title: String,
)

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
@Suppress("All JetPack Compose previews contain 'Preview' in method name")
fun MainScreenAppBarView(
    @PreviewParameter(MainScreenAppBarPreviewProvider::class) input: MainScreenAppBarInput,
    backButtonModifier: Modifier = Modifier,
    onEventHandler: (MeetingNoteAppBarEvent) -> Unit = {}
) {
    KotoxBasicTheme {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .sizeIn(minHeight = 56.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
//                Column() {
//                    IconButton(
//                        onClick = { onEventHandler.invoke(MeetingNoteAppBarEvent.AppBarHomeEvent) },
//                    ) {
//                        Icon(
//                            modifier = Modifier.onGloballyPositioned { coordinates ->
//                                //pagerPosition = coordinates.positionInRoot()
//                                onEventHandler.invoke(
//                                    MeetingNoteAppBarEvent.BackButtonOffset(
//                                        coordinates.positionInWindow(),
//                                        coordinates.size
//                                    )
//                                )
//                            },
//                            painter = painterResource(R.drawable.ic_arrow_left),
//                            contentDescription = stringResource(R.string.general_back_description),
//                            tint = LocalColors.current.textNorm,
//                        )
//                    }
//                }
                Column(verticalArrangement = Arrangement.Center) {
                    Box(modifier = Modifier.sizeIn(minHeight = 56.dp)) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = input.title,
                            style = LocalTypography.current.body1Medium,
                            color = LocalColors.current.textNorm,
                        )
                    }
                }
            }

        }

    }
}

class MainScreenAppBarPreviewProvider : PreviewParameterProvider<MainScreenAppBarInput> {
    override val values: Sequence<MainScreenAppBarInput> = sequenceOf(
        MainScreenAppBarInput(title = "Kotox Android Task")
    )
}