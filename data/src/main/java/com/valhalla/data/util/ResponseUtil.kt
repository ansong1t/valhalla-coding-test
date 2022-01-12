package com.valhalla.data.util

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Response

inline fun <reified T> ResponseBody.convert(): T? {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(T::class.java)

    return jsonAdapter.fromJson(string())
}

inline fun <reified T> Response<T>.result(
    onSuccess: T.() -> Unit,
    onError: T.() -> Unit
) {
    body()?.let {
        onSuccess.invoke(it)
    }

    errorBody()?.convert<T>()?.let {
        onError.invoke(it)
    }
}
