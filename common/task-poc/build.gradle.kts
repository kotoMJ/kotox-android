@Suppress("DSL_SCOPE_VIOLATION") // Fixes a known IntelliJ IDEA bug: https://youtrack.jetbrains.com/issue/KTIJ-19369

plugins {
    id("cz.kotox.android.library")
    id("cz.kotox.android.library.compose")
    id("cz.kotox.android.feature")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "cz.kotox.common.task.poc"
}

dependencies {

    implementation(projects.common.core)
    implementation(projects.common.ui)
    implementation(projects.common.network)
    implementation(projects.common.crypto)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.moshi.kotlin)

    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.retorofit)
    implementation(libs.retorofit.moshi)

    implementation(libs.timber)

    testImplementation(projects.test)
    testImplementation(libs.bundles.test.unit)
    androidTestImplementation(libs.bundles.test.android)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
