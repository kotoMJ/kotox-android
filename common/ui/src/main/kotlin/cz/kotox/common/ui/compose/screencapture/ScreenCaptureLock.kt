package cz.kotox.common.ui.compose.screencapture

//FIXME MJ - uncomment this with targeting Android 14

//import android.app.Activity
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//import cz.kotox.common.core.android.extension.disableScreenCapture
//import cz.kotox.common.core.android.extension.enableScreenCapture
//import cz.kotox.common.core.android.permission.getReadMediaImagesPermission
//import cz.kotox.common.core.android.screencapture.LegacyScreenCaptureDetectionDelegate
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.FlowPreview
//import timber.log.Timber
//
//@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//@OptIn(
//    FlowPreview::class,
//    ExperimentalCoroutinesApi::class, ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class
//)
//@Composable
//internal fun ScreenCaptureLock(
//    screenAllowedCondition: () -> Boolean,
//    onScreenCaptured: () -> Unit,
//    onScreenPotentiallyCaptured: () -> Unit,
//    activity: Activity?
//) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//        val screenCaptureDetectionDelegate = remember {
//            Activity.ScreenCaptureCallback {
//                onScreenCaptured.invoke()
//            }
//        }
//
//        DisposableEffect(
//            screenAllowedCondition,
//            activity?.window
//        ) {
//            val window = activity?.window
//            if (screenAllowedCondition()) {
//                window?.disableScreenCapture()
//                activity?.registerScreenCaptureCallbackSafe(screenCaptureDetectionDelegate)
//            } else {
//                activity?.unregisterScreenCaptureCallbackSafe(screenCaptureDetectionDelegate)
//                window?.enableScreenCapture()
//            }
//            onDispose {
//                activity?.unregisterScreenCaptureCallbackSafe(screenCaptureDetectionDelegate)
//                window?.enableScreenCapture()
//            }
//        }
//    } else {
//        val legacyScreenCaptureDetectionDelegate: LegacyScreenCaptureDetectionDelegate? = remember {
//            activity?.let {
//                Timber.d(">>>_ ScreenshotDetectionDelegate")
//                LegacyScreenCaptureDetectionDelegate(
//                    it,
//                    object : LegacyScreenCaptureDetectionDelegate.ScreenshotDetectionListener {
//                        override fun onScreenCaptured(path: String) {
//                            Timber.d(">>>_ ScreenshotDetectionDelegate")
//                            onScreenCaptured.invoke()
//                        }
//
//                        override fun onPotentialScreenCaptured() {
//                            Timber.w(">>>_ Request permissions, screen is  potentially captured")
//                            onScreenPotentiallyCaptured.invoke()
//                        }
//                    }
//                )
//            }
//        }
//        val permissionState = rememberPermissionState(permission = getReadMediaImagesPermission())
//        LaunchedEffect(permissionState) {
//            permissionState.launchPermissionRequest()
//        }
//
//        DisposableEffect(
//            screenAllowedCondition,
//            activity?.window
//        ) {
//            val window = activity?.window
//            if (screenAllowedCondition()) {
//                window?.disableScreenCapture()
//                legacyScreenCaptureDetectionDelegate?.startScreenshotDetection()
//            } else {
//                legacyScreenCaptureDetectionDelegate?.stopScreenshotDetection()
//                window?.enableScreenCapture()
//            }
//            onDispose {
//                legacyScreenCaptureDetectionDelegate?.stopScreenshotDetection()
//                window?.enableScreenCapture()
//            }
//        }
//    }
//}
//
//private fun Activity.unregisterScreenCaptureCallbackSafe(callback: Activity.ScreenCaptureCallback) {
//    try {
//        unregisterScreenCaptureCallback(callback)
//    } catch (notRegisteredExteption: IllegalStateException) {
//        /* It's not a problem when capture callback is not registered */
//    }
//}
//
//private fun Activity.registerScreenCaptureCallbackSafe(callback: Activity.ScreenCaptureCallback) {
//    try {
//        registerScreenCaptureCallback(mainExecutor, callback)
//    } catch (alreadyRegisteredException: IllegalStateException) {
//        /* It's not a problem when capture callback is already registered */
//    }
//}
