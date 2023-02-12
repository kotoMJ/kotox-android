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
    ":task-mobile",
    ":media-mobile",
    ":playground-mobile",
    ":core-kotox-jvm",
    ":core-kotox-test",
    ":core-kotox-network",
    ":core-kotox-ui",
    ":core-kotox-crypto",
    ":task-domain",
    ":task-detail-ui",
    ":camera-custom-ui",
    ":i18n-data",
    ":i18n-domain",
    ":i18n-ui",

)
