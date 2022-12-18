package cz.kotox.media.config

import android.content.res.Configuration
import cz.kotox.android.core.config.AppProperties
import cz.kotox.android.media.BuildConfig
import cz.kotox.media.MediaApplication

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private enum class Flavor(val flavorName: String) {
    Develop("develop"),
    Staging("staging"),
    Production("production");
}

private val AppBuildType = BuildType.values().first { it.buildTypeName == BuildConfig.BUILD_TYPE }
private val AppFlavor = Flavor.values().first { it.flavorName == BuildConfig.FLAVOR }

private const val URL = "https://api.npoint.io/"//"https://api.npoint.io/86329e68087a3d2bca54"

data class KotoxMediaAppProperties(
    override val baseUrl: String = when (AppFlavor) {
        Flavor.Develop -> URL//"https://api.jsonbin.io/v3/"
        Flavor.Staging -> URL//"https://api.jsonbin.io/v3/"
        Flavor.Production -> URL//"https://api.jsonbin.io/v3/"
    },
    override val isDevEnvironment: Boolean = AppBuildType == BuildType.Debug,
    override val networkRequestTimeoutSec: Long = 30,
    override val isDarkMode: Boolean =
        MediaApplication.application.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

) : AppProperties
