package cz.kotox.starter.config

import cz.kotox.common.core.config.AppProperties
import cz.kotox.starter.BuildConfig

private enum class BuildType(val buildTypeName: String) {
    Debug("debug"),
    Release("release")
}

private val currentAppBuildType = BuildType.entries.first { it.buildTypeName == BuildConfig.BUILD_TYPE }

data class KotoxStarterAppProperties(
    override val isDebugBuildType: Boolean = currentAppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean
) : AppProperties
