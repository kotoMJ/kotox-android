package cz.kotox.lint.extension

import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.isJava
import com.android.tools.lint.detector.api.isKotlin

val JavaContext.isJava: Boolean
    get() = isJava(this.psiFile)

val JavaContext.isKotlin: Boolean
    get() = isKotlin(this.psiFile)
