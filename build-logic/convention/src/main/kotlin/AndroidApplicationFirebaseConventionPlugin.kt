
import cz.kotox.android.extensions.catalog
import cz.kotox.android.extensions.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                //apply("com.google.firebase.firebase-perf")
                apply("com.google.firebase.crashlytics")
            }

            dependencies {
                //val bom = libs.findLibrary("firebase-bom").get()
                val bom = catalog.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                add("implementation", catalog.library("firebase.auth"))
                add("implementation", catalog.library("firebase.crashlytics"))
            }

        }
    }
}
