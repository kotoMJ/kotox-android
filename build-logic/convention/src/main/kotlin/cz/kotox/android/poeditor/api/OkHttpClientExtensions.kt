package cz.kotox.android.poeditor.api

import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Downloads a file from network and outputs it as a String.
 *
 * Returns null if the request fails.
 */
fun OkHttpClient.downloadUrlToString(fileUrl: String): String {
    val translationFileRequest = Request.Builder()
            .url(fileUrl)
            .build()
    return newCall(translationFileRequest).execute().body!!.string()
}
