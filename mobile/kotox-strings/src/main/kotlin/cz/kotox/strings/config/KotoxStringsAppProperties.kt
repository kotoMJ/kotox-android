package cz.kotox.strings.config

import android.content.res.Configuration
import cz.kotox.common.core.config.AppProperties
import cz.kotox.android.strings.BuildConfig
import cz.kotox.strings.StringsApplication

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val AppBuildType = BuildType.values().first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxStringsAppProperties(
    override val isDevEnvironment: Boolean = AppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean =
        StringsApplication.application.resources.configuration.uiMode
            .and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

) : AppProperties
