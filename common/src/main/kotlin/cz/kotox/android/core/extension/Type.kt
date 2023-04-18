package cz.kotox.android.core.extension

/**
 * This property has a custom getter which returns the object itself, so if we use it on a when block, it’s treated as an expression and the compiler will force us to specify all cases.
 * Let's use this hack for WHEN expression until new version of Kotlin where all WHEN will be exhaustive by default (Yes, breaking change if not used with this hack!!!)
 * https://youtrack.jetbrains.com/issue/KT-12380#focus=Comments-27-5034058.0-0
 */
val <T> T.exhaustive: T
    get() = this
