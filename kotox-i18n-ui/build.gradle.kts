plugins {
    id("cz.kotox.android.library")
    id("cz.kotox.android.library.compose")
    id("cz.kotox.android.feature")
}


dependencies {
    implementation(projects.kotoxI18nDataDomain)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
}
