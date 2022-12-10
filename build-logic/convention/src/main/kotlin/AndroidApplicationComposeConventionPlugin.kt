import cz.kotox.android.configureAndroidCompose
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureBaseAppModule()
        }
    }

    private fun Project.configureBasePlugins() {
        pluginManager.apply {
            apply("com.android.application")
        }
    }

    private fun Project.configureBaseAppModule() {
        extensions.configure<BaseAppModuleExtension> {
            configureAndroidCompose(this)
        }
    }

}