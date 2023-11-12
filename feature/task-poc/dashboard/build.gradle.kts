plugins {
    //alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
    alias(libs.plugins.cz.kotox.android.feature)
}

android {
    namespace = "cz.kotox.feature.task.poc.dashboard"
}

dependencies {
    implementation(projects.common.taskPoc)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
