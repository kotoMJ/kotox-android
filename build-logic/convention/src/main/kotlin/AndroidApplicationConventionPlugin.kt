import cz.kotox.android.configureKotlinAndroid
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import cz.kotox.android.extensions.catalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType


@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureBasePlugins()
            configureBaseAppModule()
            poe()
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
            defaultConfig.targetSdk = catalog.findVersion("sdk-target").get().toString().toInt()
        }
    }


    private fun Project.poe(){

        val downloadPoeStringsTask = tasks.register("downloadPoeStrings"){
            //logger.info( "DOWNLOAD HERE...")
            //error("poe fock")


        }
    }
}