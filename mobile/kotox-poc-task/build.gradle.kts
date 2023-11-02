import cz.kotox.android.Flavor
import cz.kotox.android.FlavorDimension

plugins {
    id("cz.kotox.android.application")
    id("cz.kotox.android.application.compose")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {

    defaultConfig {
        applicationId = "cz.kotox.android.task"

        val version = Versions(major = 1, minor = 0, patch = 0, build = 0)

        versionCode = version.versionCode
        versionName = version.versionName

        testInstrumentationRunner = "cz.kotox.task.list.TaskTestRunner"
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

    namespace = "cz.kotox.android.task"
}

dependencies {

    implementation(projects.common.core)
    implementation(projects.common.ui)
    implementation(projects.common.network)
    implementation(projects.common.taskPoc)
    implementation(projects.feature.taskPoc.detail)
    implementation(projects.feature.taskPoc.dashboard)

    implementation(libs.android.material)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.swiperefresh)

    implementation(libs.androidx.constraint.compose)

    implementation(libs.androidx.hilt.android)
    kapt(libs.androidx.hilt.compiler)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.moshi.kotlin)
    implementation(libs.retorofit)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.timber)

    debugImplementation(libs.leakcanary)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.unit)
    testImplementation(libs.kotlinx.coroutines.test)

    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(projects.common.taskPoc)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.hilt.android.testing)
    androidTestImplementation(libs.mockk.instrumented)
    kaptAndroidTest(libs.androidx.hilt.compiler)
}
