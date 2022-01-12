package com.valhalla.extensions

import android.content.DialogInterface
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.valhalla.common.ui.R
import com.valhalla.util.showAlertDialog

fun Fragment.showGenericErrorDialog() {
    showOkDialog(
        getString(R.string.error),
        getString(R.string.generic_error)
    )
}

fun Fragment.showOkDialog(title: String, message: String) {
    requireContext().showAlertDialog(title, message)
}

fun Fragment.showOkDialog(
    title: String,
    message: String,
    okClickListener: (DialogInterface, Int) -> Unit = { _, _ -> }
) {
    requireContext().showAlertDialog(title, message, okClickListener)
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
