package com.valhalla.data

import java.util.concurrent.TimeUnit

object Constants {

    val LOCATION_UPDATE_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(10)

    const val KM_TO_METERS_CONVERSION = 1000

    const val ACTION_UNAUTHORIZED = "com.valhalla.data.ACTION_UNAUTHORIZED"

    const val DATE_TIME_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    const val DATE_TIME_FORMAT2 = "yyyy-MM-dd hh:mm:ss"

    const val DATE_FORMAT1 = "yyyy-MM-dd"

    const val CACHE_STATE_NO_INTERNET = 60 * 60 * 24 * 7
    const val CACHE_STATE_WITH_INTERNET = 5

    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_PREFETCH_DISTANCE = DEFAULT_PAGE_SIZE * 2

    const val RESPONSE_CODE_UNAUTHORIZED = 401
    const val HEADER_BY_PASS_UNAUTHORIZED = "By-Pass-UNAUTHORIZED"
    const val HEADER_NO_AUTHENTICATION = "No-Authentication"
    const val HEADER_APPLICATION_JSON = "application/json"
    const val HEADER_TYPE_AUTHORIZATION = "Authorization"
    const val HEADER_TYPE_ACCEPT = "Accept"
    const val HEADER_TYPE_CONTENT_TYPE = "Content-type"
}
