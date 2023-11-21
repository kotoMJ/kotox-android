plugins {
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
}

android {
    namespace = "cz.kotox.konsist"
}

dependencies {

    testImplementation(libs.androidx.appcompat)

    testImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(libs.junit)

    testImplementation(libs.konsist)
}