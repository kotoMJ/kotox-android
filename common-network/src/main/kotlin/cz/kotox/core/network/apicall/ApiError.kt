package cz.kotox.core.network.apicall

import cz.kotox.android.core.error.BasicError
import cz.kotox.android.core.error.OfflineError
import cz.kotox.android.core.error.UnknownError
import cz.kotox.android.core.result.Result
import cz.kotox.android.core.result.mapError

/**
 * Error returned by API call
 */
sealed class ApiError {

    abstract val cause: Throwable?

    // HTTP error with error code
    data class HttpError(
        override val cause: Throwable?,
        val code: Int?,
        val type: String?
    ) : ApiError()

    // Error when mapping from JSON to our DTO object
    data class ParsingError(
        override val cause: Throwable
    ) : ApiError()

    // Offline error
    data class NoConnectivityError(
        override val cause: Throwable
    ) : ApiError()
}

fun ApiError.toBasicError(): BasicError = when (this) {
    is ApiError.NoConnectivityError -> OfflineError
    else -> UnknownError
}

fun <V> Result<V, ApiError>.mapErrorBasic() = mapError { it.toBasicError() }

