package com.valhalla.ui.postdetails

sealed class PostDetailsActions {
    data class Error(val message: String) : PostDetailsActions()
}
