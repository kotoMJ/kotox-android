package cz.kotox.android.extensions

import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.bundle(alias: String) = findBundle(alias).get()

internal fun VersionCatalog.library(alias: String) = findLibrary(alias).get().get().toString()

internal fun VersionCatalog.version(alias: String) = findVersion(alias).get().toString()
