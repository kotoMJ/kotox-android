plugins {
    //alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.library)
}

android {

    namespace = "cz.kotox.common.crypto"

    repositories {
        flatDir {
            dirs("libs")
        }
    }
}

dependencies {
    implementation(projects.common.core)

    /**
     * TODO Adding crypto library cause compilation problems when integrating this module into the app!
     */
    // api(linkedMapOf("name" to "crypto", "ext" to "aar"))

    implementation(libs.timber)

    testImplementation(libs.junit)
}
