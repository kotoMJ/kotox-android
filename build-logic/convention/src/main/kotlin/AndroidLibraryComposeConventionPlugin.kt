import com.android.build.gradle.LibraryExtension
import cz.kotox.android.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureLibrary()
            configureBaseDependencies()
        }
    }

    private fun Project.configureBasePlugins() {
        with(pluginManager) {
            apply("com.android.library")
        }
    }

    private fun Project.configureLibrary() {
        extensions.configure<LibraryExtension> {
            configureAndroidCompose(this)
        }
    }

    private fun Project.configureBaseDependencies() {
        dependencies {
            add("lintChecks", project(":lint"))
        }
    }

}