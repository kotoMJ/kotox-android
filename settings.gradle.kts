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
    ":core-kotox",
    ":core-kotox-network",
    ":core-kotox-ui",
    ":core-kotox-crypto",
    ":domain-kotox-task",
    ":feature-kotox-task-detail"
)
