plugins {
    id("cz.kotox.android.library")
    id("cz.kotox.android.library.compose")
}

android {
    repositories {
        flatDir {
            dirs("libs")
        }
    }
}

dependencies {
   implementation(projects.common)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.timber)


    testImplementation(libs.junit)
}
