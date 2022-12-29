repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:7.4.0-rc03") //FIXME MJ - use constant with libs.version.toml android-gradle

    // Without this dependency compose setContent is not resolved
    // Issue tracker https://issuetracker.google.com/issues/195342732
    implementation(
        kotlin(
            "gradle-plugin",
            "1.7.20"
        )
    ) //FIXME MJ - use constant with libs.version.toml kotlin
    implementation("com.squareup:javapoet:1.13.0")// required by Hilt https://github.com/google/dagger/issues/3068
}
