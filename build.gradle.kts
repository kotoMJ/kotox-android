buildscript {
    repositories {
        maven("https://jitpack.io") // POEDITOR
        google()
        mavenCentral()
    }
}

// Lists all plugins used throughout the project without applying them.
@Suppress("DSL_SCOPE_VIOLATION") // Fixes a known IntelliJ IDEA bug: https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.gradleDependencyUpdate)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.github.ben-manes.versions")

    detekt {
        buildUponDefaultConfig = true
        config = files("${project.rootDir}/extras/detekt.yml")
        parallel = true
    }

    repositories {
        google()
        mavenCentral()
    }

//    // https://github.com/ben-manes/gradle-versions-plugin
//    tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
//        // disallow release candidates as upgradable versions from stable versions
//        rejectVersionIf {
//            isNonStable(candidate.version) && !isNonStable(currentVersion)
//        }
//    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
