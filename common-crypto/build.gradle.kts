plugins {
    id("cz.kotox.android.library")
    id("com.hyperdevs.poeditor")
}

//poEditor {
//    this.setApiToken("API_TOKEN")
//    this.setProjectId(PROJECT_ID)
//    this.setDefaultLang("en")
//}

android {
    repositories {
        flatDir {
            dirs("libs")
        }
    }
}

dependencies {
   implementation(projects.common)

    /**
     * TODO Adding crypto library cause compilation problems when integrating this module into the app!
     */
    //api(linkedMapOf("name" to "crypto", "ext" to "aar"))

    implementation(libs.timber)


    testImplementation(libs.junit)
}
