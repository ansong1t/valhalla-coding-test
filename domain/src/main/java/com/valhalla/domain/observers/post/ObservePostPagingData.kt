package com.valhalla.domain.observers.post

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.paging.cachedIn
import com.valhalla.data.Constants.DEFAULT_PAGE_SIZE
import com.valhalla.data.Constants.DEFAULT_PREFETCH_DISTANCE
import com.valhalla.data.entities.post.Post
import com.valhalla.domain.PagingInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class ObservePostPagingData @Inject constructor() :
    PagingInteractor<ObservePostPagingData.Params, Int, Post>() {

    data class Params constructor(
        override val pagingConfig: PagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        override val coroutineScope: CoroutineScope,
        override val pagingSource: PagingSource<Int, Post>,
        override val remoteMediator: RemoteMediator<Int, Post>?
    ) : Parameters<Int, Post>

    override fun createObservable(params: Params): Flow<PagingData<Post>> {
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = {
                params.pagingSource
            },
            remoteMediator = params.remoteMediator
        ).flow.cachedIn(params.coroutineScope)
    }

    companion object {
        private const val PAGE_SIZE = DEFAULT_PAGE_SIZE
        private const val PREFETCH_DISTANCE = DEFAULT_PREFETCH_DISTANCE
    }
}
