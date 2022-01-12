package com.valhalla.domain.observers

import com.valhalla.data.entities.post.Post
import com.valhalla.data.repositories.post.PostRepository
import com.valhalla.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObservePost @Inject constructor(
    private val postRepository: PostRepository
) : SubjectInteractor<ObservePost.Params, Post>() {

    data class Params(val postId: Long)

    override fun createObservable(params: Params): Flow<Post> {
        return postRepository.observePost(params.postId)
    }
}
