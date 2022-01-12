package com.valhalla.data.remotesources.post

import com.valhalla.data.entities.ErrorResult
import com.valhalla.data.entities.Result
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post
import com.valhalla.data.responses.post.toDB
import com.valhalla.data.services.ApiService
import com.valhalla.data.util.HttpException
import com.valhalla.extensions.toResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService
) : PostRemoteSource {

    override suspend fun getAllPosts(): Result<List<Post>> {
        return try {
            apiService.getAllPosts()
                .toResult(
                    mapper = { it.map { post -> post.toDB() } },
                    errorMapper = { HttpException(message = "Request error") }
                )
        } catch (e: Exception) {
            ErrorResult(e)
        }
    }

    override suspend fun getPost(postId: Long): Result<Post> {
        return try {
            apiService.getPost(postId)
                .toResult(
                    mapper = { post -> post.toDB() },
                    errorMapper = { HttpException(message = "Request error") }
                )
        } catch (e: Exception) {
            ErrorResult(e)
        }
    }

    override suspend fun getComments(postId: Long): Result<List<Comment>> {
        return try {
            apiService.getComments(postId)
                .toResult(
                    mapper = { it.map { comment -> comment.toDB() } },
                    errorMapper = { HttpException(message = "Request error") }
                )
        } catch (e: Exception) {
            ErrorResult(e)
        }
    }
}
