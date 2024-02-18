package cz.kotox.auth.data.config

import cz.kotox.auth.BuildConfig
import cz.kotox.common.core.config.AppProperties

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val currentAppBuildType = BuildType.entries.first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxAuthAppProperties(
    override val isDebugBuildType: Boolean = currentAppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean
) : AppProperties
