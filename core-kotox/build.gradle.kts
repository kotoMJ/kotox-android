plugins {
    id("cz.kotox.android.library")
}

dependencies {
    implementation(projects.coreKotoxJvm)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.timber)
}

dependencies {

    implementation(libs.libphonenumber)
}