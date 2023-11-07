plugins {
    alias(libs.plugins.cz.kotox.android.library)
    alias(libs.plugins.cz.kotox.android.library.compose)
}

android {
    namespace = "cz.kotox.common.ui"
}


dependencies {

    api(libs.androidx.compose.material)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(projects.common.core)
    implementation(projects.common.coreAndroid)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.timber)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
