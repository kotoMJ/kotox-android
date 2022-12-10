import cz.kotox.android.configureKotlinAndroid
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureBaseAppModule()
        }
    }

    private fun Project.configureBasePlugins() {
        pluginManager.apply {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }
    }

    private fun Project.configureBaseAppModule() {
        extensions.configure<BaseAppModuleExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = 32
        }
    }
}