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
    ":mobile:kotox-strings",
    ":mobile:kotox-poc-task",
    ":common:core",
    ":common:core-android",
    ":common:crypto",
    ":common:network",
    ":common-test",
    ":common:text",
    ":common:ui",
    ":common:task-poc",
    ":feature:task-poc-detail",
    ":feature:task-poc-dasboard",
    ":common:camera",
    ":common:i18n",
    ":i18n-domain",
    ":i18n-ui",

)
