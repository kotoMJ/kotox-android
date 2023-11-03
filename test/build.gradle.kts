plugins {
    id("cz.kotox.android.library")
}

android {
    namespace = "cz.kotox.common.test"
}

dependencies {
    implementation(projects.common.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.bundles.test.unit)
    androidTestImplementation(libs.bundles.test.android)
}
