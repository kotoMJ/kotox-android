package cz.kotox.task.detail.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import cz.kotox.android.feature.task.detail.R
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.core.ui.theme.LocalTypography
import cz.kotox.core.ui.theme.KotoxBasicTheme
import cz.kotox.task.domain.api.factory.toTask
import cz.kotox.task.domain.impl.remote.dto.TaskDTO

data class TaskSummaryComponentInput(
    val item: TaskSummaryItem,
    val useHeadlineTitle: Boolean = false
)

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    widthDp = 350,
    name = "Light Mode"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun TaskSummaryComponent(
    @PreviewParameter(TaskSummaryComponentPreviewProvider::class) input: TaskSummaryComponentInput
) {
    KotoxBasicTheme {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),

            constraintSet = ConstraintSet {
                val title = createRefFor("title")
                val description = createRefFor("description")
                val createdIcon = createRefFor("createdIcon")
                val createdLabel = createRefFor("createdLabel")
                val createdDateTime = createRefFor("createdDateTime")
                val dueIcon = createRefFor("dueIcon")
                val dueLabel = createRefFor("dueLabel")
                val dueDateTime = createRefFor("dueDateTime")

                constrain(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
                constrain(description) {
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom)
                    width = Dimension.fillToConstraints
                }

                constrain(createdIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(description.bottom, 8.dp)
                    width = Dimension.wrapContent
                }
                constrain(createdLabel) {
                    start.linkTo(createdIcon.end, 8.dp)
                    top.linkTo(createdIcon.top)
                    bottom.linkTo(createdIcon.bottom)
                    width = Dimension.wrapContent
                }

                constrain(dueIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(createdIcon.bottom, 8.dp)
                    width = Dimension.wrapContent
                }
                constrain(dueLabel) {
                    start.linkTo(dueIcon.end, 8.dp)
                    top.linkTo(dueIcon.top)
                    bottom.linkTo(dueIcon.bottom)
                    width = Dimension.wrapContent
                }

                val dateStartBarrier = createEndBarrier(createdLabel, dueLabel)

                constrain(createdDateTime) {
                    start.linkTo(dateStartBarrier, 8.dp)
                    top.linkTo(createdIcon.top)
                    bottom.linkTo(createdIcon.bottom)
                    width = Dimension.wrapContent
                }

                constrain(dueDateTime) {
                    start.linkTo(dateStartBarrier, 8.dp)
                    top.linkTo(dueIcon.top)
                    bottom.linkTo(dueIcon.bottom)
                    width = Dimension.wrapContent
                }
            },
        ) {

            Text(
                modifier = Modifier.layoutId("title"),
                text = input.item.title,
                style = if (input.useHeadlineTitle) {
                    LocalTypography.current.headline
                } else {
                    LocalTypography.current.body1Medium
                },
                color = LocalColors.current.textNorm,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier.layoutId("description"),
                text = input.item.description,
                style = LocalTypography.current.body2Regular,
                color = LocalColors.current.textWeak,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Icon(
                modifier = Modifier.layoutId("createdIcon"),
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                tint = LocalColors.current.primary,
            )

            Text(
                modifier = Modifier.layoutId("createdLabel"),
                text = stringResource(id = R.string.task_summary_component_label_created),
                style = LocalTypography.current.body2Medium,
                color = LocalColors.current.textNorm,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier.layoutId("createdDateTime"),
                text = input.item.creationDate.toDateTimeTaskString(),
                style = LocalTypography.current.body2Regular,
                color = LocalColors.current.textNorm,
                textAlign = TextAlign.Center,
            )

            Icon(
                modifier = Modifier.layoutId("dueIcon"),
                painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = null,
                tint = LocalColors.current.primary,
            )
            Text(
                modifier = Modifier.layoutId("dueLabel"),
                text = stringResource(id = R.string.task_summary_component_label_due),
                style = LocalTypography.current.body2Medium,
                color = LocalColors.current.textNorm,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier.layoutId("dueDateTime"),
                text = input.item.dueDate.toDateTimeTaskString(),
                style = LocalTypography.current.body2Regular,
                color = LocalColors.current.textNorm,
                textAlign = TextAlign.Center,
            )
        }
    }
}

class TaskSummaryComponentPreviewProvider : PreviewParameterProvider<TaskSummaryComponentInput> {
    override val values: Sequence<TaskSummaryComponentInput> = sequenceOf(
        TaskSummaryComponentInput(
            item = TaskSummaryItem.from(
                TaskDTO(
                    creationDate = "2016-04-23T18:25:43.511Z",
                    dueDate = "2017-01-23T18:00:00.511Z",
                    encryptedDescription = "UHJvdG9uVlBOIGxhdW5jaA==",
                    encryptedTitle = "UHJvdG9uVlBO",
                    id = "1",
                    encryptedImage = "https://i.imgur.com/GTag1cl.png"
                ).toTask()
            ),
            useHeadlineTitle = false
        ),
        TaskSummaryComponentInput(
            item = TaskSummaryItem.from(
                TaskDTO(
                    creationDate = "2016-04-23T18:25:43.511Z",
                    dueDate = "2017-01-23T18:00:00.511Z",
                    encryptedDescription = "UHJvdG9uVlBOIGxhdW5jaA==",
                    encryptedTitle = "UHJvdG9uVlBO",
                    id = "1",
                    encryptedImage = "https://i.imgur.com/GTag1cl.png"
                ).toTask()
            ),
            useHeadlineTitle = true
        )
    )
}