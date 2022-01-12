package com.valhalla.network

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.valhalla.data.Constants.ACTION_UNAUTHORIZED
import com.valhalla.data.Constants.CACHE_STATE_NO_INTERNET
import com.valhalla.data.Constants.CACHE_STATE_WITH_INTERNET
import com.valhalla.data.Constants.HEADER_APPLICATION_JSON
import com.valhalla.data.Constants.HEADER_BY_PASS_UNAUTHORIZED
import com.valhalla.data.Constants.HEADER_NO_AUTHENTICATION
import com.valhalla.data.Constants.HEADER_TYPE_ACCEPT
import com.valhalla.data.Constants.HEADER_TYPE_AUTHORIZATION
import com.valhalla.data.Constants.HEADER_TYPE_CONTENT_TYPE
import com.valhalla.data.Constants.RESPONSE_CODE_UNAUTHORIZED
import com.valhalla.util.hasInternetConnection
import com.valhalla.util.isInternetAvailable
import com.valhalla.util.token
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Helper class to automatically put bearer token to [Request] header
 * and broadcast an unauthorized request
 * Add `@Headers("No-Authentication: true")` on top of your retrofit
 * request if you don't want to pass bearer token
 *
 * @param sharedPref where bearer token is saved
 */
class ServiceInterceptor(
    private val context: Context,
    private val sharedPref: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (originalRequest.header(HEADER_NO_AUTHENTICATION) == null) {
            val token = sharedPref.token
            if (!token.isNullOrEmpty()) {
                val bearerToken = "Bearer $token"
                requestBuilder
                    .addHeader(HEADER_TYPE_AUTHORIZATION, bearerToken)
            }
        }
        // add content type and accept type headers
        requestBuilder.addHeader(HEADER_TYPE_ACCEPT, HEADER_APPLICATION_JSON)
        requestBuilder.addHeader(HEADER_TYPE_CONTENT_TYPE, HEADER_APPLICATION_JSON)

        val response = chain.proceed(requestBuilder.build())

        // handle Unauthorized responses and auto logout to the app
        if (originalRequest.header(HEADER_BY_PASS_UNAUTHORIZED) == null &&
            response.code() == RESPONSE_CODE_UNAUTHORIZED
        ) {
            handleUnauthorizedResponse()
        }
        return response
    }

    private fun handleUnauthorizedResponse() {
        LocalBroadcastManager.getInstance(context)
            .sendBroadcast(Intent(ACTION_UNAUTHORIZED))
    }
}
