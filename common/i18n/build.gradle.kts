@Suppress("DSL_SCOPE_VIOLATION") // Fixes a known IntelliJ IDEA bug: https://youtrack.jetbrains.com/issue/KTIJ-19369

plugins {
    //alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
    alias(libs.plugins.cz.kotox.android.feature)
}

android {
    namespace = "cz.kotox.common.i18n"
}

dependencies {
    implementation(projects.common.core)
    implementation(projects.common.coreAndroid)
    implementation(projects.common.crypto)
    implementation(projects.common.designSystem)
    implementation(projects.common.network)
    implementation(projects.common.ui)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.moshi.kotlin)

    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.retorofit)
    implementation(libs.retorofit.moshi)

    implementation(libs.timber)

    implementation(libs.google.libphonenumber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
