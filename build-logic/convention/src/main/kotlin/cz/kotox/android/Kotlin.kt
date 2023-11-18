package cz.kotox.android

import cz.kotox.android.extensions.kotlinOptions
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.get

/**
 * Configure base Kotlin
 */
internal fun Project.configureKotlin(
    javaPluginExtension: JavaPluginExtension,
) = with(javaPluginExtension) {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    sourceSets["main"].java.srcDir("src/main/kotlin")

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
