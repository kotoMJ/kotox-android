plugins {
    //alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
}

android {
    namespace = "cz.kotox.common.test"

    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {

    api(libs.androidx.compose.runtime)

    implementation(projects.common.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.bundles.test.unit)
    androidTestImplementation(libs.bundles.test.android)
}
