package com.valhalla.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.valhalla.common.ui.databinding.DialogProgressBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun infiniteProgressDialog(
    context: Context,
    text: String? = null,
    builder: (MaterialAlertDialogBuilder.() -> Unit)? = null
): AlertDialog {
    return MaterialAlertDialogBuilder(context).apply {
        val view = DialogProgressBinding.inflate(LayoutInflater.from(context))
        view.text = text
        view.executePendingBindings()
        setView(view.root)
        builder?.invoke(this)
    }.create()
}
