package cz.kotox.android.linters

import cz.kotox.android.extensions.isAndroid
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

object KtlintConfiguration {

    fun Project.applyKtlint() {
        pluginManager.apply("org.jlleitschuh.gradle.ktlint")

        configure<KtlintExtension> {
            android.set(isAndroid)
            disabledRules.addAll("max-line-length", "curly-spacing")
            filter {
                exclude { element -> element.file.path.contains("build/") }
            }
        }
    }

}
