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
    ":kotox-task-mobile",
    ":kotox-media-mobile",
    ":kotox-playground-mobile",
    ":common",
    ":common-test",
    ":common-network",
    ":common-ui",
    ":common-crypto",
    ":task-domain",
    ":task-data",
    ":task-detail-ui",
    ":camera-ui",
    ":i18n-data",
    ":i18n-domain",
    ":i18n-ui",

)
