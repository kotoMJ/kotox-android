plugins {
    id("cz.kotox.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

dependencies {
    implementation(projects.coreKotox)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

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