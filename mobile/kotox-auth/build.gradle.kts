import cz.kotox.android.Flavor
import cz.kotox.android.FlavorDimension

plugins {
    alias(libs.plugins.cz.kotox.linters)
    alias(libs.plugins.cz.kotox.android.application)
    alias(libs.plugins.cz.kotox.android.application.compose)
    alias(libs.plugins.cz.kotox.android.poeditor)
    alias(libs.plugins.cz.kotox.android.hilt)
    alias(libs.plugins.cz.kotox.android.firebase)
}

android {

    namespace = "cz.kotox.auth"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "cz.kotox.auth"

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
    implementation(projects.common.coreAndroid)
    implementation(projects.common.designSystem)
    implementation(projects.common.i18n)
    implementation(projects.common.ui)

    implementation(projects.feature.firebaseAuth)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.timber)
    implementation(libs.android.material)
    debugImplementation(libs.leakcanary)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.hilt.compiler)
    testImplementation(libs.bundles.test.unit)
}
