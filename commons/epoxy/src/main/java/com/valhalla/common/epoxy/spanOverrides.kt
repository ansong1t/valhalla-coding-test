package com.valhalla.common.epoxy

import com.airbnb.epoxy.EpoxyModel

object TotalSpanOverride : EpoxyModel.SpanSizeOverrideCallback {
    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int) = totalSpanCount
}

object HalfSpanOverride : EpoxyModel.SpanSizeOverrideCallback {
    override fun getSpanSize(
        totalSpanCount: Int,
        position: Int,
        itemCount: Int
    ) = (totalSpanCount / 2).coerceAtLeast(1)
}
