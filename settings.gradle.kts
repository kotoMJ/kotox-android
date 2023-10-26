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
    ":kotox-mobile-media",
    //    ":kotox-mobile-memo",
    ":kotox-mobile-playground",
    ":kotox-mobile-starter",
    ":kotox-mobile-strings",
    ":kotox-mobile-task",
    ":common:core",
    ":common-android",
    ":common-crypto",
    ":common-network",
    ":common-test",
    ":common-text",
    ":common-ui",
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
