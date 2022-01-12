package com.valhalla

import androidx.navigation.NavDirections

sealed class MainActions {
    object Idle : MainActions()

    object LightStatusBar : MainActions()

    object DarkStatusBar : MainActions()

    data class NavigateTo(val navDirections: NavDirections, val destinationScreenId: Int?) :
        MainActions()

    data class Error(val message: String) : MainActions()
}
