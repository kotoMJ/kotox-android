package cz.kotox.feature.task.poc.detail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import cz.kotox.common.ui.theme.LocalColors
import cz.kotox.common.ui.theme.LocalTypography
import cz.kotox.common.ui.theme.KotoxBasicTheme
import cz.kotox.feature.task.poc.detail.R

sealed class TaskDetailAppBarEvent {
    object AppBarHomeEvent : TaskDetailAppBarEvent()
}

data class TaskDetailAppBarInput constructor(
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
fun MainScreenAppBarView(
    @PreviewParameter(MainScreenAppBarPreviewProvider::class) input: TaskDetailAppBarInput,
    backButtonModifier: Modifier = Modifier,
    onEventHandler: (TaskDetailAppBarEvent) -> Unit = {}
) {
    KotoxBasicTheme {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .sizeIn(minHeight = 56.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column() {
                    IconButton(
                        onClick = { onEventHandler.invoke(TaskDetailAppBarEvent.AppBarHomeEvent) },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = stringResource(R.string.generic_back_description),
                            tint = LocalColors.current.textNorm,
                        )
                    }
                }
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

class MainScreenAppBarPreviewProvider : PreviewParameterProvider<TaskDetailAppBarInput> {
    override val values: Sequence<TaskDetailAppBarInput> = sequenceOf(
        TaskDetailAppBarInput(title = "Task detail")
    )
}