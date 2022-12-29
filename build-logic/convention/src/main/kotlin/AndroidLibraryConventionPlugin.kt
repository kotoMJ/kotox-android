import cz.kotox.android.configureFlavors
import cz.kotox.android.configureKotlinAndroid
import com.android.build.gradle.LibraryExtension
import cz.kotox.android.extensions.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureLibrary()
        }
    }

    private fun Project.configureBasePlugins() {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }
    }

    private fun Project.configureLibrary() {

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = catalog.findVersion("sdk-target").get().toString().toInt()
            configureFlavors(this)
        }
    }

}
