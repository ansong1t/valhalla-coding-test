package com.valhalla.data.entities.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valhalla.data.entities.ValhallaEntity

@Entity(tableName = Comment.TABLE_NAME)
data class Comment(
    @PrimaryKey
    override val id: Long = 0,
    val postId: Long = 0,
    val name: String = "",
    val email: String = "",
    val body: String = ""
) : ValhallaEntity {

    companion object {
        const val TABLE_NAME = "comments"
    }
}
