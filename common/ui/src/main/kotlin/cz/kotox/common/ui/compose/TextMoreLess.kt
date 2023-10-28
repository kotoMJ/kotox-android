package cz.kotox.common.ui.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.kotox.common.ui.R
import cz.kotox.common.ui.theme.LocalColors
import cz.kotox.common.ui.theme.LocalTypography

@Composable
private fun TextMoreLess(description: String) {
    val showMore = remember { mutableStateOf(false) }
    val showMoreVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (showMoreVisible.value) {
                    showMore.value = !showMore.value
                }
            },
    ) {
        if (showMore.value) {
            Text(
                text = description,
                style = LocalTypography.current.body1Medium,
                color = LocalColors.current.textNorm,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.general_button_showLess),
                style = LocalTypography.current.body1Medium,
                color = LocalColors.current.onControlsPrimary,
            )
        } else {
            Text(
                text = description,
                style = LocalTypography.current.body1Medium,
                color = LocalColors.current.primary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    showMoreVisible.value = textLayoutResult.hasVisualOverflow
                },
            )
            if (showMoreVisible.value) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.general_button_showMore),
                    style = LocalTypography.current.body1Medium,
                    color = LocalColors.current.onControlsPrimary,
                )
            }
        }
    }
}