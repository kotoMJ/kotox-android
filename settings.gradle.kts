pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "KotoxAndroid"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":mobile-kotox-task",
    ":mobile-kotox-media",
    ":core-kotox",
    ":core-kotox-network",
    ":core-kotox-ui",
    ":core-kotox-crypto",
    ":core-kotox-i18n",
    ":domain-kotox-task",
    ":feature-kotox-task-detail",
    ":feature-kotox-camera-custom"

)
