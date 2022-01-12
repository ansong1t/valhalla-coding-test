package com.valhalla.common.navigation

import androidx.navigation.NavOptions

fun defaultNavAnimation(block: ((NavOptions.Builder) -> Unit)? = null) =
    NavOptions.Builder().apply {
        block?.invoke(this)
    }.setEnterAnim(R.anim.enter_anim)
        .setExitAnim(R.anim.exit_anim)
        .setPopEnterAnim(R.anim.pop_enter_anim)
        .setPopExitAnim(R.anim.pop_exit_anim)
        .build()
