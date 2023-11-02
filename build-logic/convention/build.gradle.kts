plugins {
    `kotlin-dsl`
}

group = "cz.kotox.android.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)

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
    }
}