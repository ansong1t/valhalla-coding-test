package com.valhalla.data.repositories.post

import androidx.paging.PagingSource
import com.valhalla.data.entities.Result
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun updatePosts()

    suspend fun updatePost(postId: Long)

    suspend fun updateComments(postId: Long)

    suspend fun getPostsPaged(): Result<PagingSource<Int, Post>>

    fun observePost(postId: Long): Flow<Post>

    fun observeComments(postId: Long): Flow<List<Comment>>
}
