import cz.kotox.android.configureAndroidCompose
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureLibrary()
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

}