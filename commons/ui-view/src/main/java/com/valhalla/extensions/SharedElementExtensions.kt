package com.valhalla.extensions

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.valhalla.SharedElementHelper
import androidx.core.util.Pair

fun SharedElementHelper.toBundle(activity: Activity): Bundle? {
    return toActivityOptions(activity)?.toBundle()
}

@Suppress("SpreadOperator")
fun SharedElementHelper.toActivityOptions(activity: Activity): ActivityOptionsCompat? {
    if (!isEmpty()) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            *sharedElements.map { Pair(it.key, it.value) }.toTypedArray()
        )
    }
    return null
}

fun SharedElementHelper.applyToTransaction(tx: FragmentTransaction) {
    for ((view, customTransitionName) in sharedElements) {
        tx.addSharedElement(view, customTransitionName!!)
    }
}

fun SharedElementHelper.toFragmentNavigatorExtras(): Navigator.Extras {
    return FragmentNavigator.Extras.Builder()
        .addSharedElements(sharedElements)
        .build()
}

fun SharedElementHelper.toActivityNavigatorExtras(activity: Activity): Navigator.Extras {
    return ActivityNavigatorExtras(toActivityOptions(activity))
}

fun sharedElementHelperOf(vararg elements: kotlin.Pair<View, String>) = SharedElementHelper().apply {
    elements.forEach { (view, transitionName) -> addSharedElement(view, transitionName) }
}
