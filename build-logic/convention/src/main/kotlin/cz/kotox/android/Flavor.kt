package cz.kotox.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

enum class FlavorDimension {
    env
}

enum class Flavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    develop(FlavorDimension.env, ".develop"),
//    staging(FlavorDimension.env, ".staging"),
//    production(FlavorDimension.env)
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.env.name
        productFlavors {
            Flavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                }
            }
        }
    }
}