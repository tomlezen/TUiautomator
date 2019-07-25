package com.tlz.tuiautomator

import com.orhanobut.logger.Logger
import java.io.Serializable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * xxx.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 14:56.
 */
@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
class TUiautomatorResult<out T> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any?
) : Serializable {

    val isSuccess: Boolean get() = value !is Failure

    val isFailure: Boolean get() = value is Failure

    inline fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    override fun toString(): String =
        when (value) {
            is Failure -> value.toString()
            else -> "Success($value)"
        }

    companion object {

        fun <T> success(value: T): TUiautomatorResult<T> = TUiautomatorResult(value)

        fun <T> failure(exception: Throwable): TUiautomatorResult<T> = TUiautomatorResult(createFailure(exception))
    }

    internal class Failure(
        @JvmField
        val exception: Throwable
    ) : Serializable {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

internal fun createFailure(exception: Throwable): Any =
    TUiautomatorResult.Failure(exception)

internal fun TUiautomatorResult<*>.throwOnFailure() {
    if (value is TUiautomatorResult.Failure) throw value.exception
}

inline fun <R> runCatching(block: () -> R): TUiautomatorResult<R> {
    return try {
        TUiautomatorResult.success(block())
    } catch (e: Throwable) {
        TUiautomatorResult.failure(e)
    }
}

inline fun <T, R> T.runTCatching(block: T.() -> R): TUiautomatorResult<R> {
    return try {
        TUiautomatorResult.success(block())
    } catch (e: Throwable) {
        Logger.e(e, "")
        TUiautomatorResult.failure(e)
    }
}

inline suspend fun <T, R> T.runTCatching1(block: T.() -> R): TUiautomatorResult<R> {
    return try {
        TUiautomatorResult.success(block())
    } catch (e: Throwable) {
        Logger.e(e, "")
        TUiautomatorResult.failure(e)
    }
}

@ExperimentalContracts
inline fun <R, T : R> TUiautomatorResult<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    contract {
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (val exception = exceptionOrNull()) {
        null -> value as T
        else -> onFailure(exception)
    }
}

inline fun <R, T : R> TUiautomatorResult<T>.getOrDefault(defaultValue: R): R {
    if (isFailure) return defaultValue
    return value as T
}

@ExperimentalContracts
inline fun <R, T> TUiautomatorResult<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (val exception = exceptionOrNull()) {
        null -> onSuccess(value as T)
        else -> onFailure(exception)
    }
}

@ExperimentalContracts
inline fun <R, T> TUiautomatorResult<T>.map(transform: (value: T) -> R): TUiautomatorResult<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when {
        isSuccess -> TUiautomatorResult.success(transform(value as T))
        else -> TUiautomatorResult(value)
    }
}

@ExperimentalContracts
inline fun <T> TUiautomatorResult<T>.onFailure(action: (exception: Throwable) -> Unit): TUiautomatorResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    exceptionOrNull()?.let { action(it) }
    return this
}

@ExperimentalContracts
inline fun <T> TUiautomatorResult<T>.onSuccess(action: (value: T) -> Unit): TUiautomatorResult<T> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (isSuccess) action(value as T)
    return this
}