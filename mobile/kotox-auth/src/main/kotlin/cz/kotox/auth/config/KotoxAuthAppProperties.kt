package cz.kotox.auth.config

import cz.kotox.auth.BuildConfig
import cz.kotox.common.core.config.AppProperties

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val currentAppBuildType = BuildType.entries.first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxAuthAppProperties(
    override val isDevEnvironment: Boolean = currentAppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean
) : AppProperties
