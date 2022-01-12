package com.valhalla.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.valhalla.data.entities.post.Comment
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CommentDao : EntityDao<Comment>() {

    @Query("SELECT * FROM ${Comment.TABLE_NAME} WHERE postId = :postId")
    abstract fun observe(postId: Long): Flow<List<Comment>>
}
