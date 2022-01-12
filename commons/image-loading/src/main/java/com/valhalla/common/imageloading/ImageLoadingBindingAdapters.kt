package com.valhalla.common.imageloading

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter(
    "load", "circleCrop", requireAll = false
)
fun ImageView.loadImageProfile(
    oldPath: String?,
    oldCircleCrop: Boolean?,
    path: String?,
    circleCrop: Boolean?
) {
    if (oldPath == null && path == null) {
        load(R.drawable.image_placeholder) {
            crossfade(true)
            if (circleCrop == true) {
                transformations(CircleCropTransformation())
            }
            error(R.drawable.image_placeholder)
        }
    } else {
        if (oldPath != path || oldCircleCrop != circleCrop) {
            load(path) {
                crossfade(true)
                if (circleCrop == true) {
                    transformations(CircleCropTransformation())
                }
                error(R.drawable.image_placeholder)
            }
        }
    }
}
