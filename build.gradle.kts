buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.androidx.hilt.gradle)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // Fixes a known IntelliJ IDEA bug: https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
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

    // https://github.com/ben-manes/gradle-versions-plugin
    tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
        // disallow release candidates as upgradable versions from stable versions
        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
