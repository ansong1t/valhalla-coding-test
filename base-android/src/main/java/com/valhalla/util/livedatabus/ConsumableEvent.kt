package com.valhalla.util.livedatabus

data class ConsumableEvent(var value: Any? = null, var isConsumed: Boolean = false) {
    /**
     *  run task & consume event after that
     */
    fun runAndConsume(runnable: () -> Unit) {
        if (!isConsumed) {
            runnable()
            isConsumed = true
        }
    }
}
