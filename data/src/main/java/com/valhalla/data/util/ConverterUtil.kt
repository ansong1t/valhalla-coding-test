package com.valhalla.data.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

inline fun <reified T> String.toObject(): T? {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(T::class.java)

    return jsonAdapter.fromJson(this)
}

inline fun <reified T> T.toJsonString(): String {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(T::class.java)

    return jsonAdapter.toJson(this)
}

inline fun <reified T> List<T>.toJsonString(): String {
    val moshi = Moshi.Builder().build()

    val type =
        Types.newParameterizedType(List::class.java, T::class.java)
    val jsonAdapter = moshi.adapter<List<T>>(type)

    return jsonAdapter.toJson(this)
}

inline fun <reified T> String.toListObject(): List<T>? {
    val moshi = Moshi.Builder().build()

    val type =
        Types.newParameterizedType(List::class.java, T::class.java)
    val jsonAdapter = moshi.adapter<List<T>>(type)

    return jsonAdapter.fromJson(this)
}
