package com.valhalla.data.remotesources.post

import com.valhalla.data.entities.Result
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post

interface PostRemoteSource {
    suspend fun getAllPosts(): Result<List<Post>>

    suspend fun getPost(postId: Long): Result<Post>

    suspend fun getComments(postId: Long): Result<List<Comment>>
}
