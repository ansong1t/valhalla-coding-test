package com.valhalla.data.localsources.post

import androidx.paging.PagingSource
import com.valhalla.data.daos.CommentDao
import com.valhalla.data.daos.PostDao
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLocalSourceImpl @Inject constructor(
    private val postDao: PostDao,
    private val commentDao: CommentDao
) : PostLocalSource {

    override suspend fun updatePosts(posts: List<Post>) {
        postDao.insertAllWithReplace(posts)
    }

    override suspend fun updatePost(post: Post) {
        postDao.insertAllWithReplace(post)
    }

    override suspend fun updateComments(comments: List<Comment>) {
        commentDao.insertAllWithReplace(comments)
    }

    override suspend fun getPostPagingSource(): PagingSource<Int, Post> {
        return postDao.getPostsPagingSource()
    }

    override fun observePost(postId: Long): Flow<Post> {
        return postDao.observe(postId)
    }

    override fun observeComments(postId: Long): Flow<List<Comment>> {
        return commentDao.observe(postId)
    }
}
