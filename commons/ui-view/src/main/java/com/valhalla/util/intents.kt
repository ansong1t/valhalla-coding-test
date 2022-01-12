package com.valhalla.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.goToAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
