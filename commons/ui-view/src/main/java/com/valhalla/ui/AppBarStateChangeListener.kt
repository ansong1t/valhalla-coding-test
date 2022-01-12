package com.valhalla.ui

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var mCurrentState =
        State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        mCurrentState = when {
            offset == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(
                        appBarLayout,
                        State.EXPANDED,
                        offset
                    )
                }
                State.EXPANDED
            }
            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(
                        appBarLayout,
                        State.COLLAPSED,
                        offset
                    )
                }
                State.COLLAPSED
            }
            else -> {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(
                        appBarLayout,
                        State.IDLE,
                        offset
                    )
                }
                State.IDLE
            }
        }
    }

    abstract fun onStateChanged(
        appBarLayout: AppBarLayout?,
        state: State?,
        offset: Int
    )
}
