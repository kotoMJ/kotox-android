import cz.kotox.android.kotlinOptions

plugins {
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.hilt)
}

android {
    namespace = "cz.kotox.common.network"
}

dependencies {
    implementation(projects.common.core)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.moshi.kotlin)

    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.retorofit)
    implementation(libs.retorofit.moshi)

    implementation(libs.timber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
