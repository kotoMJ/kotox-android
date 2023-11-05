plugins {
    alias(libs.plugins.cz.kotox.android.library)
    id("cz.kotox.android.library.compose")
    alias(libs.plugins.cz.kotox.android.hilt)
}

android {
    namespace = "cz.kotox.common.text"
}

dependencies {
    implementation(projects.common.core)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.timber)

    implementation(libs.commonmark)
    implementation(libs.bundles.coil)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
