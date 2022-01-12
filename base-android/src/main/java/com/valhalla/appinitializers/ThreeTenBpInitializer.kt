package com.valhalla.appinitializers

import android.app.Application
import com.valhalla.appinitializers.AppInitializer
import com.valhalla.util.AppCoroutineDispatchers
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.zone.ZoneRulesProvider
import javax.inject.Inject

class ThreeTenBpInitializer @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers
) : AppInitializer {
    @DelicateCoroutinesApi
    override fun init(application: Application) {
        // Init ThreeTenABP
        AndroidThreeTen.init(application)

        // Query the ZoneRulesProvider so that it is loaded on a background coroutine
        GlobalScope.launch(dispatchers.io) {
            ZoneRulesProvider.getAvailableZoneIds()
        }
    }
}
