plugins {
    alias(libs.plugins.cz.kotox.kotlin.library)
    alias(libs.plugins.android.lint)
}

dependencies {
    compileOnly(libs.lint.api)
}
