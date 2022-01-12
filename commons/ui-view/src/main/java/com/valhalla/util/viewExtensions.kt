package com.valhalla.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.core.text.HtmlCompat
import com.valhalla.common.ui.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Context.showAlertDialog(
    title: String?,
    body: String,
    dialogInterface: (DialogInterface, Int) -> Unit = { dialog, _ -> dialog.dismiss() }
) {
    if (body.isEmpty()) {
        return
    }

    val alertBuilder = MaterialAlertDialogBuilder(this)
    alertBuilder.setTitle(title)
    alertBuilder.setMessage(HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY))
    alertBuilder.setCancelable(false)
    alertBuilder.setPositiveButton(getString(android.R.string.ok), dialogInterface)
    alertBuilder.create().show()
}

fun Context.showConfirmDialog(
    title: String = "",
    body: String,
    positiveButtonText: String,
    negativeButtonText: String = "",
    positiveClickListener: () -> Unit = {},
    negativeClickListener: () -> Unit = {}
) {
    val alertBuilder = MaterialAlertDialogBuilder(this)
    title.isNotEmpty().let {
        alertBuilder.setTitle(title)
    }
    alertBuilder.setMessage(HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY))

    alertBuilder
        .setPositiveButton(
            positiveButtonText
        ) { _, _ ->
            positiveClickListener()
        }

    if (negativeButtonText.isNotEmpty()) {
        alertBuilder
            .setNegativeButton(
                negativeButtonText
            ) { _, _ ->
                negativeClickListener()
            }
    }

    alertBuilder.setCancelable(false)
    alertBuilder.create().show()
}

fun View.showGenericSuccessSnackBar(
    text: String
) {
    showSuccessSnackBar(
        text,
        context
            .getString(
                R.string.hide
            )
    ) {
        it.dismiss()
    }
}

fun View.showGenericErrorSnackBar(
    text: String
) {
    showErrorSnackBar(
        text,
        context
            .getString(
                R.string.hide
            )
    ) {
        it.dismiss()
    }
}

@SuppressLint("WrongConstant")
fun View.showSuccessSnackBar(
    text: String,
    actionText: String,
    actionClickListener: (Snackbar) -> Unit
): Snackbar {
    return Snackbar.make(this, text, SNACKBAR_DURATION)
        .apply {
            setAction(actionText) {
                actionClickListener(this)
            }
            setTextColor(
                context.getThemeColor(R.attr.colorOnAlertSuccess)
            )
            setActionTextColor(
                context.getThemeColor(R.attr.colorOnAlertSuccess)
            )
            setBackgroundTint(
                context.getThemeColor(R.attr.colorAlertSuccess)
            )
            show()
        }
}

@SuppressLint("WrongConstant")
fun View.showErrorSnackBar(
    text: String,
    actionText: String,
    actionClickListener: (Snackbar) -> Unit
): Snackbar {
    return Snackbar.make(this, text, SNACKBAR_DURATION)
        .apply {
            setAction(actionText) {
                actionClickListener(this)
            }
            setTextColor(
                context.getThemeColor(R.attr.colorOnAlertError)
            )
            setActionTextColor(
                context.getThemeColor(R.attr.colorOnAlertError)
            )
            setBackgroundTint(
                context.getThemeColor(R.attr.colorAlertError)
            )
            show()
        }
}

@ExperimentalCoroutinesApi
fun View.clicks(): Flow<View> = callbackFlow {
    setOnClickListener {
        trySend(it)
    }
    awaitClose { setOnClickListener(null) }
}

private const val SNACKBAR_DURATION = 3000
