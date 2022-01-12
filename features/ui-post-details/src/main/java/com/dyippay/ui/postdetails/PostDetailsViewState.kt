package com.valhalla.ui.postdetails

import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post

data class PostDetailsViewState(
    val postId: Long = -1,
    val postLoading: Boolean = false,
    val commentsLoading: Boolean = false,
    val post: Post = Post(),
    val comments: List<Comment> = emptyList()
)
