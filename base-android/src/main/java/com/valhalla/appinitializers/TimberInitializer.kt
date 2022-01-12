package com.valhalla.appinitializers

import android.app.Application
import com.valhalla.util.ValhallaLogger
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val qrLogger: ValhallaLogger
) : AppInitializer {
    override fun init(application: Application) = qrLogger.setup()
}
