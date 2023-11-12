import cz.kotox.android.linters.DetektConfiguration.applyDetekt
import cz.kotox.android.linters.KtlintConfiguration.applyKtlint
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class LintersConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.applyDetekt()
        target.applyKtlint()
    }
}