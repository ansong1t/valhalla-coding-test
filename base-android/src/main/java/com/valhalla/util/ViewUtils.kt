package com.valhalla.util

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.Dimension

fun Context.dpToPx(
    @Dimension(unit = Dimension.DP) dp: Int
): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)

fun Bitmap.blurRenderScript(context: Context, radius: Int): Bitmap {
    var smallBitmap1 = this
    try {
        smallBitmap1 = rgb65toArgb888(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val bitmap = Bitmap.createBitmap(
        smallBitmap1.width, smallBitmap1.height,
        Bitmap.Config.ARGB_8888
    )
    val renderScript = RenderScript.create(context)
    val blurInput = Allocation.createFromBitmap(renderScript, smallBitmap1)
    val blurOutput = Allocation.createFromBitmap(renderScript, bitmap)
    val blur = ScriptIntrinsicBlur.create(
        renderScript,
        Element.U8_4(renderScript)
    )
    blur.setInput(blurInput)
    blur.setRadius(radius.toFloat()) // radius must be 0 < r <= 25
    blur.forEach(blurOutput)
    blurOutput.copyTo(bitmap)
    renderScript.destroy()
    return bitmap
}

@Throws(Exception::class)
fun rgb65toArgb888(img: Bitmap): Bitmap {
    val numPixels = img.width * img.height
    val pixels = IntArray(numPixels)

    // Get JPEG pixels.  Each int is the color values for one pixel.
    img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)

    // Create a Bitmap of the appropriate format.
    val result =
        Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)

    // Set RGB pixels.
    result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
    return result
}

fun View.setMargins(l: Int, t: Int, r: Int, b: Int) {
    if (layoutParams is MarginLayoutParams) {
        val p = layoutParams as MarginLayoutParams
        p.setMargins(l, t, r, b)
        requestLayout()
    }
}

fun View.findParentByTag(tag: Any): View? =
    when {
        parent == null -> null
        (parent as? View)?.tag == tag -> parent as? View
        else -> (parent as View).findParentByTag(tag)
    }
