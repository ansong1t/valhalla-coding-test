package com.valhalla.appmodules

import com.valhalla.appinitializers.AppInitializer
import com.valhalla.appinitializers.ThreeTenBpInitializer
import com.valhalla.appinitializers.TimberInitializer
import com.valhalla.util.ValhallaLogger
import com.valhalla.util.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun provideLogger(bind: ValhallaLogger): Logger

    @Binds
    @IntoSet
    abstract fun provideThreeTenAbpInitializer(bind: ThreeTenBpInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer
}
