package cz.kotox.common.core.error

sealed class BasicError

object OfflineError : BasicError()
object UnknownError : BasicError()
