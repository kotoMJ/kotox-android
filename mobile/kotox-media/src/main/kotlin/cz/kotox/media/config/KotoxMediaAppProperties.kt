package cz.kotox.media.config

import android.content.res.Configuration
import cz.kotox.common.core.config.AppProperties
import cz.kotox.android.media.BuildConfig
import cz.kotox.media.MediaApplication

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val AppBuildType = BuildType.values().first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxMediaAppProperties(
    override val isDevEnvironment: Boolean = AppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean =
        MediaApplication.application.resources.configuration.uiMode
            .and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

) : AppProperties
