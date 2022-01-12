package com.valhalla

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.valhalla.util.ObservableLoadingCounter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ReduxViewModel<MainViewState>(MainViewState()) {

    private val _actions = Channel<List<MainActions>>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private var currentDestinationId: Int = -1

    private val loading = ObservableLoadingCounter()

    init {
        viewModelScope.launch {
            loading.observable.collect { active ->
                setState { copy(loading = active) }
            }
        }
    }

    fun navigate(
        navDirections: NavDirections,
        destinationScreenId: Int?
    ) {
        if (currentDestinationId == destinationScreenId) return // if navigating to same screen return
        viewModelScope.launch {
            _actions.send(
                listOf(
                    MainActions.NavigateTo(
                        navDirections,
                        destinationScreenId
                    )
                )
            )
        }
    }

    fun handleNavigationDestination(destinationId: Int) {
        currentDestinationId = destinationId
        viewModelScope.launch {
            val actions = arrayListOf<MainActions>()
            actions.add(handleLightStatusBar(destinationId))

            _actions.send(actions)

            handleViewComponentsVisibility(destinationId)
        }
    }

    private suspend fun handleViewComponentsVisibility(destinationId: Int) {
        val showBottomNav = destinationId !in NO_BOTTOM_NAV_IDS
        setState {
            copy(
                showBottomNav = showBottomNav
            )
        }
    }

    private fun handleLightStatusBar(destinationId: Int): MainActions {
        return if (destinationId in DARK_STATUS_BAR_NAV_IDS) MainActions.DarkStatusBar
        else MainActions.LightStatusBar
    }

    companion object {
        private val NO_BOTTOM_NAV_IDS = arrayOf<Int>()

        private val DARK_STATUS_BAR_NAV_IDS = arrayOf<Int>()
    }
}
