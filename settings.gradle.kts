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
    ":common:camera",
    ":common:core",
    ":common:core-android",
    ":common:crypto",
    ":common:design-system",
    ":common:i18n",
    ":common:network",
    ":common:task-poc",
    ":common:text",
    ":common:ui",
    ":feature:task-poc:detail",
    ":feature:task-poc:dashboard",
    ":mobile:kotox-auth",
    ":mobile:kotox-media",
    ":mobile:kotox-playground",
    ":mobile:kotox-starter",
    ":mobile:kotox-strings",
    ":mobile:kotox-poc-task",
    ":test",
    ":lint",
    ":konsist_test"

)
