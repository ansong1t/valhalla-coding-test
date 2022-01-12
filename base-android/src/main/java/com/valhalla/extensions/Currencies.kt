package com.valhalla.extensions

import kotlin.math.abs

/**
 *
 * This shows negative values in Peso ₱
 */
fun Float.toAmountFormat(): String {
    return when {
        isNaN() -> "NaN"
        this < 0 -> "-₱%,.02f".format(abs(this)) // negative value
        else -> "+₱%,.02f".format(this) // positive value
    }
}
