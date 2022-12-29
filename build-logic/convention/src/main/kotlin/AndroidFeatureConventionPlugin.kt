import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureBaseDependencies()
        }
    }

    private fun Project.configureBasePlugins() {
        pluginManager.apply {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.kapt")
            apply("dagger.hilt.android.plugin")
        }
    }

    private fun Project.configureBaseDependencies() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {

            add("implementation", project(":core-kotox"))
            add("implementation", project(":core-kotox-network"))
            add("implementation", project(":core-kotox-ui"))

            add("implementation", libs.findLibrary("androidx.activity.compose").get())
            add("implementation", libs.findLibrary("androidx.appcompat").get())

            add("implementation", libs.findLibrary("androidx.compose.material").get())
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling").get())

            add("implementation", libs.findLibrary("androidx.constraint.compose").get())

            add("implementation", libs.findLibrary("coil.kt").get())
            add("implementation", libs.findLibrary("coil.kt.compose").get())

            add("implementation", libs.findLibrary("kotlin.stdlib").get())
            add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())

            add("implementation", libs.findLibrary("timber").get())

            add("implementation", libs.findLibrary("androidx.hilt.compose").get())
            add("implementation", libs.findLibrary("androidx.hilt.android").get())
            add("kapt", libs.findLibrary("androidx.hilt.compiler").get())
        }
    }
}
