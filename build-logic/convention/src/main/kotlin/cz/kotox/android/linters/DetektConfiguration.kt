package cz.kotox.android.linters

import cz.kotox.android.extensions.isAndroid
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

object DetektConfiguration {
    fun Project.applyDetekt() {
        pluginManager.apply("io.gitlab.arturbosch.detekt")

        pluginManager.withPlugin("io.gitlab.arturbosch.detekt") {
            configure<DetektExtension> {
                buildUponDefaultConfig = true
                config = files("${project.rootDir}/extras/detekt.yml")
                parallel = true
            }
        }
    }
}
