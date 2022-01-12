package com.valhalla.ui

import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView

class MaxLinesToggleClickListener(private val collapsedLines: Int) : View.OnClickListener {
    private val transition = ChangeBounds().apply {
        duration = 200
        interpolator = FastOutSlowInInterpolator()
    }

    override fun onClick(view: View) {
        TransitionManager.beginDelayedTransition(findParent(view), transition)
        val textView = view as TextView
        textView.maxLines = if (textView.maxLines > collapsedLines) collapsedLines else Int.MAX_VALUE
    }

    private fun findParent(view: View): ViewGroup {
        var parentView: View? = view
        while (parentView != null) {
            val parent = parentView.parent as View?
            if (parent is RecyclerView) {
                return parent
            }
            parentView = parent
        }
        // If we reached here we didn't find a RecyclerView in the parent tree, so lets just use our parent
        return view.parent as ViewGroup
    }
}
