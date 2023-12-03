import cz.kotox.android.Flavor
import cz.kotox.android.FlavorDimension

plugins {
    //alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.application)
    alias(libs.plugins.cz.kotox.android.application.compose)
    alias(libs.plugins.cz.kotox.android.poeditor)
    alias(libs.plugins.cz.kotox.android.hilt)
}

android {
    namespace = "cz.kotox.playground"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "cz.kotox.playground"

        // val version = Versions(major = 1, minor = 0, patch = 0, build = 0)

        versionCode = 1000 // version.versionCode
        versionName = "1.0.0.0" // version.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            signingConfig = signingConfigs.getByName("debug")
        }

        // @see Flavor for more details on the app product flavors.
        flavorDimensions += FlavorDimension.env.name
        productFlavors {
            Flavor.values().forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name
                    if (flavor.applicationIdSuffix != null) {
                        applicationIdSuffix = flavor.applicationIdSuffix
                    }
                }
            }
        }
    }

    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {
    lintChecks(projects.lint)

    implementation(libs.androidx.compose.material3) // customize slider

    implementation(projects.common.core)
    implementation(projects.common.designSystem)
    implementation(projects.common.i18n)
    implementation(projects.common.ui)

    implementation(libs.android.material)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.androidx.compose.material)
//    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.swiperefresh)

    implementation(libs.androidx.constraint.compose)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.moshi.kotlin)
    implementation(libs.retorofit)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.timber)

    debugImplementation(libs.leakcanary)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.hilt.compiler)
    testImplementation(libs.bundles.test.unit)
}
