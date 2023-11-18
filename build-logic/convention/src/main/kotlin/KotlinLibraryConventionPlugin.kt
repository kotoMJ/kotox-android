import cz.kotox.android.configureKotlin
import cz.kotox.android.extensions.library
import cz.kotox.android.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureLibrary()
        }
    }

    private fun Project.configureBasePlugins() {
        with(pluginManager) {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
        }
    }

    private fun Project.configureLibrary() {
        extensions.configure<JavaPluginExtension> {
            configureKotlin(this)
        }
    }

    private fun Project.configureBaseDependencies() {
        dependencies {
            add("testImplementation", libs.library("junit"))

            add("testImplementation", libs.library("kotlin.test"))
            add("testImplementation", libs.library("kotlinx.coroutines.test"))
        }
    }
}
