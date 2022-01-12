package com.valhalla.ui.home

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.valhalla.data.entities.post.Post
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class HomeEpoxyController :
    PagingDataEpoxyController<Post>() {

    var callbacks: Callbacks? = null

    interface Callbacks {
        fun onItemClicked(post: Post)
    }

    override fun buildItemModel(
        currentPosition: Int,
        item: Post?
    ): EpoxyModel<*> {
        return PostBindingModel_()
            .id("item_${currentPosition}_${item?.id}")
            .title(item?.title)
            .body(item?.body)
            .onClick(View.OnClickListener {
                item?.let { callbacks?.onItemClicked(item) }
            })
    }
}
