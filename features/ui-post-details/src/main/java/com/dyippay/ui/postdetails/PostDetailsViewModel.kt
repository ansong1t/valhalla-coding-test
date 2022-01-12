package com.valhalla.ui.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.valhalla.ReduxViewModel
import com.valhalla.domain.interactors.UpdateComments
import com.valhalla.domain.interactors.UpdatePost
import com.valhalla.domain.observers.ObserveComments
import com.valhalla.domain.observers.ObservePost
import com.valhalla.util.ObservableLoadingCounter
import com.valhalla.util.collectInto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updatePost: UpdatePost,
    private val updateComments: UpdateComments,
    private val observePost: ObservePost,
    private val observeComments: ObserveComments
) : ReduxViewModel<PostDetailsViewState>(
    PostDetailsViewState(
        postId = (savedStateHandle.get<Long>(ARGS_KEY_POST_ID)) ?: 0
    )
) {

    private val postLoading = ObservableLoadingCounter()
    private val commentsLoading = ObservableLoadingCounter()
    private val _action = Channel<PostDetailsActions>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        viewModelScope.launch {
            postLoading.observable.collect { loading ->
                setState { copy(postLoading = loading) }
            }
        }
        viewModelScope.launch {
            commentsLoading.observable.collect { loading ->
                setState { copy(commentsLoading = loading) }
            }
        }

        viewModelScope.launch {
            observeComments.observe()
                .collect {
                    setState { copy(comments = it) }
                }
        }
        observeComments(ObserveComments.Params(currentState().postId))

        viewModelScope.launch {
            observePost.observe()
                .collect {
                    setState { copy(post = it) }
                }
        }
        observePost(ObservePost.Params(currentState().postId))

        updatePost()
        updateComments()
    }

    private fun updatePost() {
        viewModelScope.launch {
            updatePost(UpdatePost.Params(currentState().postId))
                .collectInto(postLoading)
        }
    }

    private fun updateComments() {
        viewModelScope.launch {
            updateComments(UpdateComments.Params(currentState().postId))
                .collectInto(commentsLoading)
        }
    }

    companion object {
        private const val ARGS_KEY_POST_ID = "postId"
    }
}
