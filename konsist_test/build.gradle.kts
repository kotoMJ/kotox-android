plugins {
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
}

android {
    namespace = "cz.kotox.konsist"

    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
        jniLibs {
            useLegacyPackaging = true
        }
    }

    defaultConfig {
        testInstrumentationRunner = "cz.kotox.task.list.TaskTestRunner"
    }
}
dependencies {

    testImplementation(libs.androidx.appcompat)

    testImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.junit)

    testImplementation(libs.konsist)
}