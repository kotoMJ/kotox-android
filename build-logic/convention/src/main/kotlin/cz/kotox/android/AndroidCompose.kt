package cz.kotox.android

import com.android.build.api.dsl.CommonExtension
import cz.kotox.android.extensions.catalog
import cz.kotox.android.extensions.library
import cz.kotox.android.extensions.version
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = catalog.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {
            val bom = catalog.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            //add("implementation", catalog.version("android-material"))
            // Add ComponentActivity to debug manifest
            add("debugImplementation", catalog.findLibrary("androidx.compose.ui.test-manifest").get())
            add("implementation", catalog.library("androidx.compose.material"))
            add("implementation", catalog.library("androidx.activity.compose"))
        }

    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs
        }
    }
}