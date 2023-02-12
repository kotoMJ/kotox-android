package cz.kotox.playground.config

import android.content.res.Configuration
import cz.kotox.android.core.config.AppProperties
import cz.kotox.android.media.BuildConfig
import cz.kotox.playground.PlaygroundApplication

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val AppBuildType = BuildType.values().first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxPlaygroundAppProperties(
    override val isDevEnvironment: Boolean = AppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean =
        PlaygroundApplication.application.resources.configuration.uiMode
            .and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

) : AppProperties
