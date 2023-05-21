plugins {
    id("cz.kotox.android.library")
    id("cz.kotox.android.library.compose")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(projects.common)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.hilt.android)
    kapt(libs.androidx.hilt.compiler)


    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.timber)

    implementation(libs.commonmark)
    implementation(libs.bundles.coil)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
