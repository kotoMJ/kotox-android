package cz.kotox.starter.config

import android.content.res.Configuration
import cz.kotox.android.core.config.AppProperties
import cz.kotox.android.starter.BuildConfig
import cz.kotox.starter.StarterApplication

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val AppBuildType = BuildType.values().first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxStarterAppProperties(
    override val isDevEnvironment: Boolean = AppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean =
        StarterApplication.application.resources.configuration.uiMode
            .and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

) : AppProperties
