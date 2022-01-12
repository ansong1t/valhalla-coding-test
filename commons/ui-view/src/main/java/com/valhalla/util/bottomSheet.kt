package com.valhalla.util

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.valhalla.common.ui.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * Setting bottomSheet height, half expanded height
 * @param peekHeight set to null for to set value same as halfExpandedRatio
 * */
fun View.applyBottomSheetHeight(
    fullExpandedRatio: Float = 0.7f,
    halfExpandedRatio: Float = 0.4f,
    isFitToContents: Boolean = true,
    peekHeight: Int? = null,
    additionalAction: (BottomSheetBehavior<View>) -> Unit = { _ -> }
) {
    val params: ViewGroup.LayoutParams? = layoutParams
    val bottomSheetBehavior = BottomSheetBehavior.from<View>(this)
    val windowHeight: Int = context.getWindowHeight()
    if (params != null && layoutParams != null) {

        params.height = (windowHeight * fullExpandedRatio).toInt()

        layoutParams = params
        bottomSheetBehavior.isFitToContents = isFitToContents
        bottomSheetBehavior.halfExpandedRatio = halfExpandedRatio
        bottomSheetBehavior.peekHeight = peekHeight ?: (windowHeight * halfExpandedRatio).toInt()
    }
    additionalAction.invoke(bottomSheetBehavior)
}

fun Context.createBottomSheetBackground(
    fillColor: ColorStateList = ColorStateList.valueOf(Color.WHITE),
    elevation: Float = resources.getDimension(R.dimen.elevation_bottom_sheet)
): MaterialShapeDrawable {
    val shapeAppearanceModel =
        // Create a ShapeAppearanceModel with the same shapeAppearanceOverlay used in the style
        ShapeAppearanceModel.builder(this, 0, R.style.ShapeAppearance_Baseplate_BottomSheet)
            .build()

    val newMaterialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    newMaterialShapeDrawable.initializeElevationOverlay(this)
    newMaterialShapeDrawable.fillColor = fillColor
    newMaterialShapeDrawable.elevation = elevation
    return newMaterialShapeDrawable
}

private fun Context.getWindowHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val displayMetrics = DisplayMetrics()
        display?.let {
            it.getRealMetrics(displayMetrics)
            displayMetrics.heightPixels
        } ?: -1
    } else {
        val displayMetrics = (this as? Activity)?.getScreenDisplayMetrics()
        return displayMetrics?.heightPixels ?: -1
    }
}
