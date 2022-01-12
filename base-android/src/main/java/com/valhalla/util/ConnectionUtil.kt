package com.valhalla.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

@SuppressLint("MissingPermission")
fun Context.hasInternetConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return if (activeNetwork != null && activeNetwork.isConnected) {
        try {
            val url = URL("http://www.google.com/")
            val urlC: HttpURLConnection = url.openConnection() as HttpURLConnection
            urlC.setRequestProperty("User-Agent", "test")
            urlC.setRequestProperty("Connection", "close")
            urlC.connectTimeout = 1000 // mTimeout is in seconds
            urlC.connect()
            urlC.responseCode == 200
        } catch (e: IOException) {
            false
        }
    } else false
}

/**
 * This method actually checks if device is connected to internet
 * (There is a possibility it's connected to a network but not to internet).
 * */

fun isInternetAvailable(): Boolean {
    return try {
        val ipAddress: InetAddress = InetAddress.getByName("google.com")
        // You can replace it with your name
        !ipAddress.equals("")
    } catch (e: Exception) {
        false
    }
}
