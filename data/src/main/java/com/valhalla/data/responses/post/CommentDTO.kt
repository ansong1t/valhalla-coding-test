package com.valhalla.data.responses.post

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.valhalla.data.entities.post.Comment

@JsonClass(generateAdapter = true)
data class CommentDTO(
    @Json(name = "postId")
    val postId: Long = 0,
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "body")
    val body: String = ""
)

fun CommentDTO.toDB() = Comment(
    id = id,
    postId = postId,
    name = name,
    email = email,
    body = body
)
