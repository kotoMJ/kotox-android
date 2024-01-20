package cz.kotox.feature.task.poc.detail.ui

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
import cz.kotox.common.designsystem.theme.KotoxBasicTheme
import cz.kotox.common.task.poc.ui.TaskSummaryComponent
import cz.kotox.common.task.poc.ui.TaskSummaryComponentInput
import cz.kotox.common.task.poc.ui.TaskSummaryComponentPreviewProvider

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
@Suppress("All JetPack Compose previews contain 'Preview' in method name")
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
