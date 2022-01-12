package com.valhalla.data

import com.valhalla.data.daos.CommentDao
import com.valhalla.data.daos.PostDao

interface ValhallaDatabase {
    fun postDao(): PostDao
    fun commentDao(): CommentDao
}
