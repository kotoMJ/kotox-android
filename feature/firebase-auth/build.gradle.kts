plugins {
    alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
    alias(libs.plugins.cz.kotox.android.feature)
    alias(libs.plugins.cz.kotox.android.hilt)
    alias(libs.plugins.cz.kotox.android.firebase)
}

android {
    namespace = "cz.kotox.feature.firebase.auth"
}

dependencies {
    implementation(projects.common.taskPoc)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
