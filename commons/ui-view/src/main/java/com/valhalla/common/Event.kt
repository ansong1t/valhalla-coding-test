package com.valhalla.common

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(block: ((T) -> Unit)? = null): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            block?.invoke(content)
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun <T> T.toEvent() = Event(this)
