package cz.kotox.common.network.apicall

import cz.kotox.android.core.result.Result
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import timber.log.Timber

@Suppress("TooGenericExceptionCaught")
suspend fun <T> apiCall(
    call: suspend () -> T,
): Result<T, ApiError> = try {
    val response = call()
    Result.Value(response)
} catch (e: JsonDataException) {
    // Moshi parsing error
    Timber.e(e)
    Result.Error(ApiError.ParsingError(e))
} catch (e: HttpException) {
    val responseError = e.response()?.parseErrJsonResponse<ResponseErrorBody>()
    // Http error from server
    Result.Error(ApiError.HttpError(e, code = e.code(), type = responseError?.type))
} catch (e: Exception) {
    // Other error - most likely connection
    Timber.v(e)
    Result.Error(ApiError.NoConnectivityError(e))
}
