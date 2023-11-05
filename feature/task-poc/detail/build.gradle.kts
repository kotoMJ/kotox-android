plugins {
    alias(libs.plugins.cz.kotox.android.library)
    id("cz.kotox.android.library.compose")
    id("cz.kotox.android.feature")
}

android {
    namespace = "cz.kotox.feature.task.poc.detail"
}

dependencies {
    implementation(projects.common.taskPoc)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
