package com.valhalla.domain.interactors

import com.valhalla.data.repositories.post.PostRepository
import com.valhalla.domain.Interactor
import com.valhalla.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePosts @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val postRepository: PostRepository
) : Interactor<UpdatePosts.Params>() {

    object Params

    override suspend fun doWork(params: Params) {
        return withContext(appCoroutineDispatchers.io) {
            postRepository.updatePosts()
        }
    }
}
