package com.valhalla.data.entities.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valhalla.data.entities.ValhallaEntity

@Entity(tableName = Post.TABLE_NAME)
data class Post(
    @PrimaryKey
    override val id: Long = 0,
    val userId: Long = 0,
    val title: String = "",
    val body: String = ""
) : ValhallaEntity {

    companion object {
        const val TABLE_NAME = "posts"
    }
}


