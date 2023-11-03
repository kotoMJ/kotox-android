plugins {
    id("cz.kotox.android.library")
    id("cz.kotox.android.library.compose")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
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

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.timber)

    implementation(libs.commonmark)
    implementation(libs.bundles.coil)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
