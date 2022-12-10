package cz.kotox.android.core.error

sealed class BasicError

object OfflineError : BasicError()
object UnknownError : BasicError()
