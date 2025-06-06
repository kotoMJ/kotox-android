package cz.kotox.common.i18n.ui.phone.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicTheme
import cz.kotox.common.designsystem.theme.shiraz.LocalColors
import cz.kotox.common.designsystem.theme.shiraz.LocalTypography
import cz.kotox.common.i18n.domain.model.CountryUiModel

@Composable
fun PhoneNumberPrefixSearchItem(
    item: cz.kotox.common.i18n.domain.model.CountryUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = item.flagEmoji
        )
        if (item is cz.kotox.common.i18n.domain.model.CountryUiModelValueItem) {
            Text(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(horizontal = 4.dp),
                style = LocalTypography.current.body1Medium,
                color = LocalColors.current.textNorm,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = "${item.countryName} (+${item.countryCode})"
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark"
)
@SuppressWarnings("UnusedPrivateMember")
@Composable
private fun PhoneNumberPrefixSearchItemPreview() {
    KotoxBasicTheme {
        PhoneNumberPrefixSearchItem(
            item = CountryUiModel.CountryUiModelFallbackItem(),
            modifier = Modifier.background(LocalColors.current.background)
        )
    }
}
