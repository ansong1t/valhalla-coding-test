package com.valhalla

import android.app.Application
import com.valhalla.appinitializers.AppInitializers
import com.valhalla.inject.MainApplicationScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

@HiltAndroidApp
class AppController : Application() {
    @Inject
    lateinit var initializers: AppInitializers

    @Inject
    @MainApplicationScope
    lateinit var mainScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mainScope.cancel()
    }
}
