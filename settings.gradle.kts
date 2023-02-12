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
    ":mobile-kotox-playground",
    ":core-kotox-jvm",
    ":core-kotox-test",
    ":core-kotox-network",
    ":core-kotox-ui",
    ":core-kotox-crypto",
    ":data-domain-kotox-task",
    ":feature-kotox-task-detail",
    ":feature-kotox-camera-custom",
    ":i18n-data",
    ":i18n-domain",
    ":i18n-ui",

)
