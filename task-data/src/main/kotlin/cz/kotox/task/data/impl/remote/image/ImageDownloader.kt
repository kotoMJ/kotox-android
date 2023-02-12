package cz.kotox.task.data.impl.remote.image

import android.content.Context
import android.os.Environment
import android.os.StrictMode
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

/**
 * ImageDownloader is currently solved by instance because there is no synchronization mechanism
 * implemented yet. So we can have more downloads in time, but all will target the same file
 * so it's acceptable solution for now.
 */
class ImageDownloader {

    fun download(context: Context, imageUrl: String, taskId: String): String? {

        return try {
            val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .toString() + "/" + "${taskId}.png"


            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val url = URL(imageUrl)
            val connection = url.openConnection()
            connection.connect()
            val fileLength: Int = connection.contentLength

            val input: InputStream = BufferedInputStream(url.openStream())
            val output: OutputStream = FileOutputStream(path)


            val data = ByteArray(2048)
            var total: Long = 0
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()
                //TODO here we can compute progress for larger images
                //onProgress((total * 100 / fileLength).toInt())
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            path
        } catch (e: Exception) {
            Timber.e(e, ">>>_ localImageUrl download failed!")
            null
        }
    }
}