@Suppress("DSL_SCOPE_VIOLATION") // Fixes a known IntelliJ IDEA bug: https://youtrack.jetbrains.com/issue/KTIJ-19369

plugins {
    id("cz.kotox.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    alias(libs.plugins.ksp)
}

dependencies {

    implementation(projects.common.core)
    implementation(projects.common.network)
    implementation(projects.common.crypto)

    implementation(projects.common.task)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.hilt.android)
    kapt(libs.androidx.hilt.compiler)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.moshi.kotlin)

    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.retorofit)
    implementation(libs.retorofit.moshi)

    implementation(libs.timber)

    testImplementation(projects.commonTest)
    testImplementation(libs.bundles.test.unit)
    androidTestImplementation(libs.bundles.test.android)

//    testImplementation(libs.junit)
//
//    androidTestImplementation(libs.androidx.test.ext.junit)
}
