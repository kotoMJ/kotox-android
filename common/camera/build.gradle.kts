plugins {
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
    alias(libs.plugins.cz.kotox.android.feature)
    alias(libs.plugins.cz.kotox.linters)
}

android {
    namespace = "cz.kotox.common.camera.custom"
}

dependencies {
    lintChecks(projects.lint)

    implementation(projects.common.coreAndroid) // prefs,enhanced permissions
    implementation(projects.common.designSystem)

    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.compose.material3) // customize slider

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
