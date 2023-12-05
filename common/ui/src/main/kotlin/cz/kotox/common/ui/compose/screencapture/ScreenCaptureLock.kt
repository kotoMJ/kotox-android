package cz.kotox.common.ui.compose.screencapture

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import cz.kotox.common.core.android.extension.disableScreenCapture
import cz.kotox.common.core.android.extension.enableScreenCapture

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
internal fun ScreenCaptureLock(
    screenAllowedCondition: () -> Boolean,
    onScreenCaptured: () -> Unit,
    activity: Activity?
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        val screenCaptureDetectionDelegate = remember {
            Activity.ScreenCaptureCallback {
                onScreenCaptured.invoke()
            }
        }

        DisposableEffect(
            screenAllowedCondition,
            activity?.window
        ) {
            val window = activity?.window
            if (screenAllowedCondition()) {
                window?.disableScreenCapture()
                activity?.registerScreenCaptureCallbackSafe(screenCaptureDetectionDelegate)
            } else {
                activity?.unregisterScreenCaptureCallbackSafe(screenCaptureDetectionDelegate)
                window?.enableScreenCapture()
            }
            onDispose {
                activity?.unregisterScreenCaptureCallbackSafe(screenCaptureDetectionDelegate)
                window?.enableScreenCapture()
            }
        }
    } else {

        /**
         * LegacyScreenCapture detection is not implemented on purpose. The hack with observing file storage
         * is too error prone (and therefore risky). It would be also a lot of legacy code to be removed in the future.
         * Let's wait with backwards compatible screen capture detection for official API.
         */
        DisposableEffect(
            screenAllowedCondition,
            activity?.window
        ) {
            val window = activity?.window
            if (screenAllowedCondition()) {
                window?.disableScreenCapture()
            } else {
                window?.enableScreenCapture()
            }
            onDispose {
                window?.enableScreenCapture()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private fun Activity.unregisterScreenCaptureCallbackSafe(callback: Activity.ScreenCaptureCallback) {
    try {
        unregisterScreenCaptureCallback(callback)
    } catch (notRegisteredExteption: IllegalStateException) {
        /* It's not a problem when capture callback is not registered */
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private fun Activity.registerScreenCaptureCallbackSafe(callback: Activity.ScreenCaptureCallback) {
    try {
        registerScreenCaptureCallback(mainExecutor, callback)
    } catch (alreadyRegisteredException: IllegalStateException) {
        /* It's not a problem when capture callback is already registered */
    }
}
