package com.valhalla.extensions

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

inline fun View.showPopupMenu(
    anchorGravity: Int = Gravity.BOTTOM,
    items: Array<String>,
    crossinline onItemClicked: (MenuItem) -> Boolean
) {
    PopupMenu(context, this, anchorGravity).apply {
        items.forEach { menu.add(it) }
        setOnMenuItemClickListener {
            onItemClicked(it)
        }
    }.show()
}
