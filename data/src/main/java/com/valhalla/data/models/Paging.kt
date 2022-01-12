package com.valhalla.data.models

data class Paging<T>(
    val list: List<T>,
    val nextPage: Int? = null
)
