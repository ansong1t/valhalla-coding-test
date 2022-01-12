package com.valhalla.data.repositories.post

import androidx.paging.PagingSource
import com.valhalla.data.entities.Result
import com.valhalla.data.entities.Success
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post
import com.valhalla.data.localsources.post.PostLocalSource
import com.valhalla.data.remotesources.post.PostRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postRemoteSource: PostRemoteSource,
    private val postLocalSource: PostLocalSource
) : PostRepository {

    override suspend fun updatePosts() {
        val posts = postRemoteSource.getAllPosts().getOrThrow()
        postLocalSource.updatePosts(posts)
    }

    override suspend fun updatePost(postId: Long) {
        val post = postRemoteSource.getPost(postId).getOrThrow()
        postLocalSource.updatePost(post)
    }

    override suspend fun updateComments(postId: Long) {
        val comments = postRemoteSource.getComments(postId).getOrThrow()
        postLocalSource.updateComments(comments)
    }

    override suspend fun getPostsPaged(): Result<PagingSource<Int, Post>> {
        return Success(postLocalSource.getPostPagingSource())
    }

    override fun observePost(postId: Long): Flow<Post> {
        return postLocalSource.observePost(postId)
    }

    override fun observeComments(postId: Long): Flow<List<Comment>> {
        return postLocalSource.observeComments(postId)
    }
}
