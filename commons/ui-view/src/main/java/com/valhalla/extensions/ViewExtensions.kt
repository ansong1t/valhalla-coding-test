package com.valhalla.extensions

import android.content.Context
import android.graphics.Rect
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.OneShotPreDrawListener
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun ViewGroup.beginDelayedTransition(duration: Long = 200) {
    TransitionManager.beginDelayedTransition(this, AutoTransition().apply { setDuration(duration) })
}

fun View.getBounds(rect: Rect) {
    rect.set(left, top, right, bottom)
}

/**
 * Call [View.requestApplyInsets] in a safe away. If we're attached it calls it straight-away.
 * If not it sets an [View.OnAttachStateChangeListener] and waits to be attached before calling
 * [View.requestApplyInsets].
 */
fun View.requestApplyInsetsWhenAttached() = doOnAttach {
    it.requestApplyInsets()
}

fun View.doOnAttach(f: (View) -> Unit) {
    if (isAttachedToWindow) {
        f(this)
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                f(v)
                removeOnAttachStateChangeListener(this)
            }

            override fun onViewDetachedFromWindow(v: View) {
                removeOnAttachStateChangeListener(this)
            }
        })
    }
}

/**
 * Allows easy listening to layout passing. Return [true] if you need the listener to keep being
 * attached.
 */
inline fun <V : View> V.doOnLayouts(crossinline action: (view: V) -> Boolean) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        @Suppress("UNCHECKED_CAST")
        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            if (!action(view as V)) {
                view.removeOnLayoutChangeListener(this)
            }
        }
    })
}

/**
 * Allows easy listening to layout passing. Return [true] if you need the listener to keep being
 * attached.
 */
inline fun View.doOnSizeChange(crossinline action: (view: View) -> Boolean) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            if ((bottom - top) != (oldBottom - oldTop) || (right - left) != (oldRight - oldLeft)) {
                if (!action(view)) {
                    view.removeOnLayoutChangeListener(this)
                }
            }
        }
    })
}

suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->
    val listener = object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            // Remove the listener
            view.removeOnLayoutChangeListener(this)
            // And resume the continuation
            cont.resume(Unit)
        }
    }
    // If the coroutine is cancelled, remove the listener
    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    // And finally add the listener to view
    addOnLayoutChangeListener(listener)
}

suspend fun View.awaitLayout() {
    if (!isLaidOut) {
        awaitNextLayout()
    }
}

suspend fun View.awaitPreDraw() = suspendCancellableCoroutine<Unit> { cont ->
    val listener = OneShotPreDrawListener.add(this) {
        cont.resume(Unit)
    }
    // If the coroutine is cancelled, remove the listener
    cont.invokeOnCancellation {
        listener.removeListener()
    }
}

suspend fun View.awaitAnimationFrame() = suspendCancellableCoroutine<Unit> { cont ->
    val runnable = Runnable {
        cont.resume(Unit)
    }
    // If the coroutine is cancelled, remove the callback
    cont.invokeOnCancellation { removeCallbacks(runnable) }
    // And finally post the runnable
    postOnAnimation(runnable)
}

fun View.showKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    requestFocus()
}

fun View.showKeyboard() {
    showKeyboard(context)
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hideKeyboardClearFocus() {
    hideKeyboard()
    clearFocus()
}

fun TextView.makeLinks(links: Array<String>, clickableSpans: Array<ClickableSpan>) {
    val spannableString = SpannableString(text)

    for (i in links.indices) {
        val clickableSpan = clickableSpans[i]
        val link = links[i]

        val startIndexOfLink = text.indexOf(link)

        spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    movementMethod = LinkMovementMethod.getInstance()
    setText(spannableString, TextView.BufferType.SPANNABLE)
}
