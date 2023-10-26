plugins {
    id("cz.kotox.android.library")
}

android {
    repositories {
        flatDir {
            dirs("libs")
        }
    }
}

dependencies {
    implementation(projects.common2)

    /**
     * TODO Adding crypto library cause compilation problems when integrating this module into the app!
     */
    // api(linkedMapOf("name" to "crypto", "ext" to "aar"))

    implementation(libs.timber)

    testImplementation(libs.junit)
}
