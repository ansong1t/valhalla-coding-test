package com.valhalla.data.services

import com.valhalla.data.responses.post.CommentDTO
import com.valhalla.data.responses.post.PostDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getAllPosts(): Response<List<PostDTO>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Long): Response<PostDTO>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Long): Response<List<CommentDTO>>
}
