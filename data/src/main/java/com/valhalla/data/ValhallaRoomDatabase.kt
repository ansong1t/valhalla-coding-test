package com.valhalla.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.valhalla.data.entities.post.Comment
import com.valhalla.data.entities.post.Post
import dev.matrix.roomigrant.GenerateRoomMigrations

@Database(
    entities = [
        Post::class,
        Comment::class
    ],
    version = DB_VERSION
)
@TypeConverters()
@GenerateRoomMigrations
abstract class ValhallaRoomDatabase : RoomDatabase(), ValhallaDatabase

const val DB_NAME = "valhalla.db"
const val DB_VERSION = 1
