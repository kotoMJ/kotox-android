package cz.kotox.common.core.android.extension

import android.view.Window
import android.view.WindowManager

fun Window?.disableScreenCapture() {
    this?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
}

fun Window?.enableScreenCapture() {
    this?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
}
