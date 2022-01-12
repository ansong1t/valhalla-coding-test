package com.valhalla.data.util

import android.content.SharedPreferences

const val PREF_MAIN = "valhalla"
const val PREF_BEARER_TOKEN = "com.valhalla.BEARER_TOKEN"
const val PREF_CURRENT_USER_LOCATION = "com.valhalla.CURRENT_USER_LOCATION"
const val PREF_SCANNED_QR_CODE = "com.valhalla.PREF_SCANNED_QR_CODE"

var SharedPreferences.token: String?
    set(value) = edit().putString(PREF_BEARER_TOKEN, value).apply()
    get() = getString(PREF_BEARER_TOKEN, null)

fun SharedPreferences.isLoggedIn(): Boolean = token?.isNotEmpty() ?: false

var SharedPreferences.scannedQRCode: String?
    set(value) = edit().putString(PREF_SCANNED_QR_CODE, value)
        .apply()
    get() = getString(PREF_SCANNED_QR_CODE, null)

fun SharedPreferences.clear() =
    edit().clear().commit()
