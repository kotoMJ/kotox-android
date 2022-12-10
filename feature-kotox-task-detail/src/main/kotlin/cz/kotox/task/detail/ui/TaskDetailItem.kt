package cz.kotox.task.detail.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.task.detail.ui.component.TaskSummaryComponent
import cz.kotox.task.detail.ui.component.TaskSummaryComponentInput
import cz.kotox.task.detail.ui.component.TaskSummaryComponentPreviewProvider

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
fun TaskDetailItem(
    @PreviewParameter(TaskSummaryComponentPreviewProvider::class) input: TaskSummaryComponentInput
) {
    KotoxBasicTheme {

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row {
                TaskSummaryComponent(
                    input = TaskSummaryComponentInput(
                        item = input.item,
                        useHeadlineTitle = false
                    )
                )
            }
        }
    }
}