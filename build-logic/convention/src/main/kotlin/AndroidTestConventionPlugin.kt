import cz.kotox.android.configureKotlinAndroid
import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureTest()
        }
    }

    private fun Project.configureBasePlugins() {
        with(pluginManager) {
            apply("com.android.test")
            apply("org.jetbrains.kotlin.android")
        }
    }

    private fun Project.configureTest() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        extensions.configure<TestExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.findVersion("sdk-target").get().toString().toInt()
        }
    }

}