package com.valhalla.domain.observers

import com.valhalla.data.entities.post.Comment
import com.valhalla.data.repositories.post.PostRepository
import com.valhalla.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveComments @Inject constructor(
    private val postRepository: PostRepository
) : SubjectInteractor<ObserveComments.Params, List<Comment>>() {

    data class Params(val postId: Long)

    override fun createObservable(params: Params): Flow<List<Comment>> {
        return postRepository.observeComments(params.postId)
    }
}
