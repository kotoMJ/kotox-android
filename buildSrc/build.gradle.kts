repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:7.4.0-rc03")

    // Without this dependency compose setContent is not resolved
    // Issue tracker https://issuetracker.google.com/issues/195342732
    implementation(kotlin("gradle-plugin", "1.6.21"))
}
