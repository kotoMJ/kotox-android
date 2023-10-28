package cz.kotox.common.core.android.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalPermissionsApi
@Composable
fun Permission(
    permission: String,
    permissionNotAvailableContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val permissionState = rememberPermissionState(permission)

    val permissionDataStore = PermissionPersistence(LocalContext.current)
    val permissionNotRequestedYet =
        permissionDataStore.permissionNotRequestedYet(permission).collectAsState(initial = false)

    val scope = rememberCoroutineScope()


    if (permissionNotRequestedYet.value) {
        /**
         * Ask for permission for very first time since shouldShowRequestPermissionRationale returns false also for
         * the situation if the user has never been asked for permission before.
         */
        SideEffect {
            permissionState.launchPermissionRequest()
            scope.launch {
                permissionDataStore.savePermissionRequest(permission)
            }
        }
    }

    when (val status = permissionState.status) {
        PermissionStatus.Granted -> {
            content.invoke()
        }

        is PermissionStatus.Denied -> {
            if (status.shouldShowRationale) {
                //Explain need for permission

                SideEffect(effect = {
                    try {
                        permissionState.launchPermissionRequest()
                    } catch (e: Exception) {
                        /**
                         * When user rejects already permitted permission, shouldShowRationale is for some reason true but launchPermissionRequest will fail
                         * in MutablePermissionState to java.lang.IllegalStateException: ActivityResultLauncher cannot be null
                         */
                        Timber.e(e)
                    }
                })
            } else {
                //permission fully denied. Go to settings to enable
                permissionNotAvailableContent.invoke()
            }
        }
    }
}
