plugins {
    alias(libs.plugins.cz.kotox.android.library)
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

    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)

    testImplementation(libs.konsist)
}