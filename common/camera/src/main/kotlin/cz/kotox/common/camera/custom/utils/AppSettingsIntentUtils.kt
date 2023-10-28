package cz.kotox.common.camera.custom.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

object AppSettingsIntentUtils {

    fun getAppSettingsIntent(context: Context): Intent {
        val appSettingsIntent = Intent()
        appSettingsIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts(
            "package", context.packageName,
            null
        )
        appSettingsIntent.data = uri
        return appSettingsIntent
    }

}