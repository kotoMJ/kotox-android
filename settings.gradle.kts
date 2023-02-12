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
    ":kotox-mobile-task",
    ":kotox-mobile-media",
    ":kotox-mobile-playground",
    ":common",
    ":common-test",
    ":common-network",
    ":common-ui",
    ":common-crypto",
    ":task-domain",
    ":task-data",
    ":task-ui",
    ":task-feature-detail",
    ":task-feature-dasboard",
    ":camera-ui",
    ":i18n-data",
    ":i18n-domain",
    ":i18n-ui",

)
