plugins {
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
    alias(libs.plugins.cz.kotox.android.feature)
    alias(libs.plugins.cz.kotox.linters)
}

android {
    namespace = "cz.kotox.feature.firebase.auth.anonymous"
}

dependencies {
    implementation(projects.common.designSystem)
    implementation(projects.common.taskPoc)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
