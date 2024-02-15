package cz.kotox.common.designsystem.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Pixel4XL Dark"
)
@Preview(
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Pixel4XL Light"
)
annotation class PreviewMobileLarge

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Pixel Light"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Pixel Dark"
)
annotation class PreviewMobileSmall

@Preview(
    name = "GalaxyTabs7FE_portrait",
    device = "spec:shape=Normal,width=680,height=1024,unit=dp,dpi=340"
)
@Preview(
    name = "GalaxyTabs7FE_landscape",
    device = "spec:shape=Normal,width=1024,height=680,unit=dp,dpi=340"
)
annotation class PreviewTablet

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class PreviewTheme