plugins {
    kotlin("jvm")
//    alias(libs.plugins.cz.kotox.linters)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(libs.libphonenumber)
    // api(linkedMapOf("name" to "crypto", "ext" to "aar"))
}
