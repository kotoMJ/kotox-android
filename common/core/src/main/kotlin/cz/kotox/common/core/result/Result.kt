@file:Suppress("TooManyFunctions")

package cz.kotox.common.core.result

sealed class Result<out V, out E> {
    data class Value<out V>(val value: V) : Result<V, Nothing>()
    data class Error<out E>(val error: E) : Result<Nothing, E>()
}

inline infix fun <V, V2, E> Result<V, E>.map(func: (V) -> V2): Result<V2, E> = when (this) {
    is Result.Value -> Result.Value(func(value))
    is Result.Error -> this
}

inline infix fun <V, E, E2> Result<V, E>.mapError(func: (E) -> E2): Result<V, E2> = when (this) {
    is Result.Value -> this
    is Result.Error -> Result.Error(func(error))
}

inline infix fun <V, V2, E> Result<List<V>, E>.mapIterable(func: (V) -> V2): Result<List<V2>, E> =
    when (this) {
        is Result.Value -> Result.Value(value.map(func))
        is Result.Error -> this
    }

inline infix fun <V, V2, E> Result<V, E>.flatMap(func: (V) -> Result<V2, E>): Result<V2, E> =
    when (this) {
        is Result.Value -> func(value)
        is Result.Error -> this
    }

inline infix fun <V, E> Result<V, E>.apply(func: (V) -> Unit): Result<V, E> =
    when (this) {
        is Result.Value -> {
            func(value)
            this
        }

        is Result.Error -> this
    }

inline infix fun <V, E, E2> Result<V, E>.flatMapError(func: (E) -> Result<V, E2>): Result<V, E2> =
    when (this) {
        is Result.Value -> this
        is Result.Error -> func(error)
    }

inline fun <V, V2, E, R> Result<V, E>.zip(
    second: Result<V2, E>,
    zipFce: (V, V2) -> Result<R, E>
): Result<R, E> =
    when (val first = this) {
        is Result.Value -> when (second) {
            is Result.Error -> second
            is Result.Value -> zipFce(first.value, second.value)
        }

        is Result.Error -> first
    }

inline fun <V, E> Result<V, E>.doOnValue(action: (V) -> Unit) = also {
    if (this is Result.Value) {
        action(this.value)
    }
}

inline fun <V, E, A> Result<V, E>.fold(v: (V) -> A, e: (E) -> A): A = when (this) {
    is Result.Value -> v(this.value)
    is Result.Error -> e(this.error)
}

fun <V, E> Result<V, E>.ignoreValue() = map { }
