package com.valhalla.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun Context.flowBroadcasts(intentFilter: IntentFilter): Flow<Intent> {
    val resultChannel = MutableStateFlow(Intent())

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            resultChannel.value = intent
        }
    }

    return resultChannel
        .onStart {
            LocalBroadcastManager.getInstance(this@flowBroadcasts)
                .registerReceiver(receiver, intentFilter)
        }.onCompletion {
            LocalBroadcastManager.getInstance(this@flowBroadcasts)
                .unregisterReceiver(receiver)
        }
}
