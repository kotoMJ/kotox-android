package cz.kotox.task.list.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.core.ui.theme.KotoxBasicTheme
import coil.compose.AsyncImage
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
@Composable
fun MainScreenTaskItem(
    modifier: Modifier = Modifier,
    @PreviewParameter(TaskSummaryComponentPreviewProvider::class) input: TaskSummaryComponentInput
) {
    KotoxBasicTheme {

        Box(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row {
                Column() {
                    Box() {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(LocalColors.current.divider)
                        )
                        AsyncImage(
                            model = input.item.localImageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .height(0.dp)
                        .width(12.dp)
                )

                Column() {
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
}