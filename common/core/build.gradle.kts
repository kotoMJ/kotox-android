plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(libs.libphonenumber)
    // api(linkedMapOf("name" to "crypto", "ext" to "aar"))
}
