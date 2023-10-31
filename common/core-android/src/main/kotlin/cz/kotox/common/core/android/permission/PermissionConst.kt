package cz.kotox.common.core.android.permission

import android.Manifest
import android.os.Build

fun getReadMediaImagesPermission(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_IMAGES
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}