package com.valhalla.data.responses.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.valhalla.data.entities.post.Post

@JsonClass(generateAdapter = true)
data class PostDTO(
    @Json(name = "userId")
    val userId: Long = 0,
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "body")
    val body: String = ""
)

fun PostDTO.toDB() = Post(
    id = id,
    userId = userId,
    title = title,
    body = body
)
