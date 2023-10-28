package cz.kotox.task.list.config

import android.content.res.Configuration
import cz.kotox.common.core.config.AppProperties
import cz.kotox.android.task.BuildConfig
import cz.kotox.common.network.config.AppNetworkingProperties
import cz.kotox.task.list.TaskApplication

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

//Powered by https://www.npoint.io/ && https://picsum.photos/
private const val URL = "https://api.npoint.io/"//"https://api.npoint.io/86329e68087a3d2bca54"

data class KotoxTaskAppProperties(

    override val isDevEnvironment: Boolean = AppBuildType == BuildType.Debug,
    override val isDarkMode: Boolean =
        TaskApplication.application.resources.configuration.uiMode
            .and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

) : AppProperties

data class KotoxTaskAppNetworkingProperties(
    override val baseUrl: String = when (AppFlavor) {
        Flavor.Develop -> URL//"https://api.jsonbin.io/v3/"
        Flavor.Staging -> URL//"https://api.jsonbin.io/v3/"
        Flavor.Production -> URL//"https://api.jsonbin.io/v3/"
    },
    override val networkRequestTimeoutSec: Long = 30,

    ) : AppNetworkingProperties
