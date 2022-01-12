package com.valhalla.ui.home

import androidx.paging.PagingData
import com.valhalla.data.entities.post.Post

sealed class HomeActions {

    data class PostsFetched(val items: PagingData<Post>) :
        HomeActions()

    data class NavigateToPostDetails(val postId: Long) : HomeActions()

    data class Error(val message: String) : HomeActions()
}
