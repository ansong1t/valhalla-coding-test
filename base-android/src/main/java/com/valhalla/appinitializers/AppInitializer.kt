package com.valhalla.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
