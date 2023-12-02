package cz.kotox.common.designsystem.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Pixel4XL"
)
@Preview(
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Pixel4XL"
)
annotation class PreviewMobileLarge

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Pixel"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Pixel"
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