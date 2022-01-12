package com.valhalla.data.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.valhalla.data.entities.post.Post
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PostDao : EntityDao<Post>() {

    @Query("SELECT * FROM ${Post.TABLE_NAME}")
    abstract fun getPostsPagingSource(): PagingSource<Int, Post>

    @Query("SELECT * FROM ${Post.TABLE_NAME} WHERE id = :postId")
    abstract fun observe(postId: Long): Flow<Post>
}
