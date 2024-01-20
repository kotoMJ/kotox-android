package cz.kotox.common.core.android.extension

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.WindowManager

fun Activity.lockDeviceRotation(value: Boolean) {
    requestedOrientation = if (value) {
        val currentOrientation: Int = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        }
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }
}

fun Activity.lockDeviceRotationPortrait(value: Boolean) {
    requestedOrientation = if (value) {
        ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }
}