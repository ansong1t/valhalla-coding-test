package com.valhalla.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors

inline fun <reified T : Context> Context.findBaseContext(): T? {
    var ctx: Context? = this
    do {
        if (ctx is T) {
            return ctx
        }
        if (ctx is ContextWrapper) {
            ctx = ctx.baseContext
        }
    } while (ctx != null)

    // If we reach here, there's not an Context of type T in our Context hierarchy
    return null
}

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

@ColorInt
fun Context.getThemeColor(@AttrRes attribute: Int) =
    MaterialColors.getColor(this, attribute, Color.TRANSPARENT)

fun Context.openBrowser(url: String) =
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()
