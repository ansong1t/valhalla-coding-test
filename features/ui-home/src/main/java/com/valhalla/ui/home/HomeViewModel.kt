package com.valhalla.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.valhalla.ReduxViewModel
import com.valhalla.base.InvokeStarted
import com.valhalla.base.InvokeSuccess
import com.valhalla.data.entities.post.Post
import com.valhalla.domain.interactors.GetPostLocalPagingSource
import com.valhalla.domain.interactors.UpdatePosts
import com.valhalla.domain.observers.post.ObservePostPagingData
import com.valhalla.util.Logger
import com.valhalla.util.ObservableLoadingCounter
import com.valhalla.util.collectInto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updatePosts: UpdatePosts,
    private val observePostPagingData: ObservePostPagingData,
    private val getPostLocalPagingSource: GetPostLocalPagingSource,
    private val logger: Logger
) : ReduxViewModel<HomeViewState>(
    HomeViewState()
) {

    private val loading = ObservableLoadingCounter()

    private val _action = Channel<HomeActions>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        viewModelScope.launch {
            loading.observable.collect { active ->
                setState { copy(loading = active) }
            }
        }

        viewModelScope.launch {
            loading.addLoader()
            observePostPagingData.observe()
                // Map to add headers
                .catch { logger.e(it) }
                .collectLatest {
                    logger.e("Collect Latest observePostPagingData")
                    loading.removeLoader()
                    _action.send(HomeActions.PostsFetched(it))
                }
        }
        refresh()
    }

    fun refresh(fromUser: Boolean = false) {
        viewModelScope.launch {
            updatePosts.invoke(UpdatePosts.Params)
                .collectInto(loading) {
                    if (it !is InvokeStarted) {
                        createPagingSource()
                    }
                }
        }
    }

    private fun createPagingSource() {
        viewModelScope.launch {
            getPostLocalPagingSource.invoke(GetPostLocalPagingSource.Params)
                .collect {
                    if (it.isSuccessful()) {
                        observePostPagingData(
                            ObservePostPagingData.Params(
                                coroutineScope = viewModelScope,
                                pagingSource = it.getOrThrow(),
                                remoteMediator = null
                            )
                        )
                    } else {
                        _action.send(HomeActions.Error("Unexpected error occurred."))
                    }
                }
        }
    }

    fun postClicked(post: Post) {
        viewModelScope.launch {
            _action.send(HomeActions.NavigateToPostDetails(post.id))
        }
    }
}
