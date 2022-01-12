package com.valhalla.api

sealed class UiStatus

object UiIdle : UiStatus()

data class UiError(val message: String) : UiStatus()

@Suppress("FunctionNaming")
fun UiError(t: Throwable): UiError = UiError(t.message ?: "Error occurred: $t")

data class UiLoading(val fullRefresh: Boolean = true) : UiStatus()

object UiSuccess : UiStatus()
