package cz.kotox.android

import com.android.build.api.dsl.CommonExtension
import cz.kotox.android.extensions.catalog
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = catalog.findVersion("androidx-compose-compiler").get().toString()
            //libs.versions.androidx.compose.compiler.get()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs
        }
    }
}