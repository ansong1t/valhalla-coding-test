package com.valhalla.domain.interactors

import androidx.paging.PagingSource
import com.valhalla.data.entities.post.Post
import com.valhalla.data.repositories.post.PostRepository
import com.valhalla.domain.ResultInteractor
import com.valhalla.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPostLocalPagingSource @Inject constructor(
    private val postRepository: PostRepository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ResultInteractor<GetPostLocalPagingSource.Params, PagingSource<Int, Post>>() {

    object Params

    override suspend fun doWork(params: Params): PagingSource<Int, Post> {
        return withContext(appCoroutineDispatchers.io) {
            postRepository.getPostsPaged().getOrThrow()
        }
    }
}