package com.valhalla.util

import android.content.SharedPreferences
import java.util.UUID

const val PREF_MAIN = "valhalla"
const val PREF_BEARER_TOKEN = "com.valhalla.BEARER_TOKEN"
const val PREF_FCM_TOKEN = "com.valhalla.FCM_TOKEN"
const val PREF_CURRENT_USER_LOCATION = "com.valhalla.CURRENT_USER_LOCATION"

var SharedPreferences.token: String?
    set(value) = edit().putString(PREF_BEARER_TOKEN, value).apply()
    get() = getString(PREF_BEARER_TOKEN, null)

fun SharedPreferences.isLoggedIn(): Boolean = token?.isNotEmpty() ?: false

fun SharedPreferences.clear() =
    edit().clear().commit()

private var uniqueID: String? = null
private const val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

fun SharedPreferences.deviceId(): String {
    if (uniqueID == null) {
        uniqueID = getString(PREF_UNIQUE_ID, null)
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString()
            val editor: SharedPreferences.Editor = edit()
            editor.putString(PREF_UNIQUE_ID, uniqueID)
            editor.apply()
        }
    }
    return uniqueID!!
}

var SharedPreferences.fcmToken: String?
    set(value) = edit().putString(PREF_FCM_TOKEN, value).apply()
    get() = getString(PREF_FCM_TOKEN, null)
