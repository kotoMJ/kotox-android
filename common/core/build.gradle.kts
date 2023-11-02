plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

    implementation(libs.libphonenumber)
    // api(linkedMapOf("name" to "crypto", "ext" to "aar"))
}
