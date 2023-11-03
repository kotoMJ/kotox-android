plugins {
    id("cz.kotox.android.library")
    id("cz.kotox.android.library.compose")
    id("cz.kotox.android.feature")
}

android {
    namespace = "cz.kotox.common.camera.custom"
}

dependencies {

    implementation(projects.common.coreAndroid) //prefs,enhanced permissions
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.accompanist.permissions)

    implementation(libs.androidx.compose.material3) // customize slider

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
