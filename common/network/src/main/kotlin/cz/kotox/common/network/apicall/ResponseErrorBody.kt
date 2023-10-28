package cz.kotox.common.network.apicall

import cz.kotox.common.network.di.NetworkModule
import com.squareup.moshi.Json
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.Response
import timber.log.Timber

private val moshi = NetworkModule.provideMoshi()

/**
 * Error response body that we receive in case of HTTP error.
 */
internal data class ResponseErrorBody(
    @Json(name = "message") val message: String,
    @Json(name = "type") val type: String,
)

internal inline fun <reified T> Response<*>.parseErrJsonResponse(): T? {
    val parser = moshi.adapter(T::class.java)
    val response = errorBody()?.string()
    if (response != null)
        try {
            return parser.fromJson(response)
        } catch (e: JsonDataException) {
            Timber.v(e, "Incorrect error type!")
        } catch (e: JsonEncodingException) {
            Timber.v(e, "Incorrect error type!")
        }
    return null
}
