package com.valhalla.extensions

import android.util.Patterns

fun CharSequence?.isEmailValid(): Boolean =
    if (this.isNullOrBlank()) false
    else Patterns.EMAIL_ADDRESS.matcher(this).matches()
