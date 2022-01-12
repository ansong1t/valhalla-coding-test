package com.valhalla.data.localsources.post

import androidx.paging.PagingSource
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalSource {
    suspend fun updatePosts(posts: List<Post>)

    suspend fun updatePost(post: Post)

    suspend fun updateComments(comments: List<Comment>)

    suspend fun getPostPagingSource(): PagingSource<Int, Post>

    fun observePost(postId: Long): Flow<Post>

    fun observeComments(postId: Long): Flow<List<Comment>>
}
