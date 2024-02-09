
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "cz.kotox.android.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ktint.gradlePlugin)

    //PoEditor dependencies
    implementation(libs.kotlin.stdlib)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    implementation(libs.retorofit)
    implementation(libs.retorofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "cz.kotox.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "cz.kotox.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "cz.kotox.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "cz.kotox.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "cz.kotox.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidTest") {
            id = "cz.kotox.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }

        register("androidPoEditor") {
            id = "cz.kotox.android.poeditor"
            implementationClass = "AndroidPoEditorPlugin"
        }

        register("androidHilt") {
            id = "cz.kotox.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("linters") {
            id = "cz.kotox.linters"
            implementationClass = "LintersConventionPlugin"
        }

        register("kotlinLibrary") {
            id = "cz.kotox.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("androidFirebase") {
            id = "cz.kotox.android.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
    }
}