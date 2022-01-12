package com.valhalla.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CurrencyTextInputEditText(
    context: Context,
    attrs: AttributeSet?
) : TextInputEditText(context, attrs) {

    private var watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.toString() == "0") {
                removeTextChangedListener(this)
                setText("")
                addTextChangedListener(this)
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    init {
        addTextChangedListener(watcher)
    }
}
