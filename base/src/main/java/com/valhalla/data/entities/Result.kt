package com.valhalla.data.entities

sealed class Result<T> {
    open fun get(): T? = null

    fun getOrThrow(): T = when (this) {
        is Success -> get()
        is ErrorResult -> throw throwable
    }

    fun isSuccessful() = this is Success

    fun error() = this as ErrorResult
}

data class Success<T>(val data: T, val responseModified: Boolean = true) : Result<T>() {
    override fun get(): T = data
}

data class ErrorResult<T>(val throwable: Throwable) : Result<T>()

suspend fun <T, R> Result<T>.map(mapper: suspend (T) -> R): Result<R> {
    return try {
        val value = getOrThrow()
        val mapped = mapper(value)
        Success(mapped)
    } catch (e: Exception) {
        ErrorResult(e)
    }
}

suspend fun <T, R> Result<T>.flatMap(mapper: suspend (T) -> Result<R>): Result<R> {
    return try {
        val value = getOrThrow()
        mapper(value)
    } catch (e: Exception) {
        ErrorResult(e)
    }
}
