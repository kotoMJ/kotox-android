package cz.kotox.camera.custom.files

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {
    /**
     * Get URI to image received from capture by camera.
     * URI location: sdcard/Android/data/com.aisense.otter.../files/Pictures/pickImageResult12345.jpeg
     *
     * @param context used to access Android APIs, like content resolve, it is your
     * activity/fragment/widget.
     */
    fun getCameraImageOutputUri(context: Context): Uri? {
        val outputFileUri: Uri?
        val getImage: File?

        // We have this because of a HUAWEI path bug when we use getUriForFile
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getImage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            outputFileUri = try {
                FileProvider.getUriForFile(
                    context,
                    /*BuildConfig.FILE_PROVIDER_AUTHORITY*/"cz.kotox.android.camera.custom", //FIXME MJ - add file provider authority
                    File.createTempFile("pickImageResult", ".jpeg", getImage)
                )
            } catch (e: Exception) {
                Uri.fromFile(File(getImage!!.path, "pickImageResult.jpeg"))
            }
        } else {
            getImage = context.externalCacheDir
            outputFileUri = Uri.fromFile(File(getImage!!.path, "pickImageResult.jpeg"))
        }
        return outputFileUri
    }
}