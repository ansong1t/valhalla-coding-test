package com.valhalla.appmodules

import com.valhalla.data.Constants
import com.valhalla.inject.DefaultDateTimeFormatter
import com.valhalla.inject.SimpleDateFormatter
import com.valhalla.inject.MainApplicationScope
import com.valhalla.util.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.MainScope
import java.text.SimpleDateFormat
import java.util.Locale

@InstallIn(SingletonComponent::class)
@Module
object AppModules {

    @Provides
    fun provideAppCoroutineDispatchers() = AppCoroutineDispatchers()

    @Provides
    @MainApplicationScope
    fun provideMainScope() = MainScope()

    @Provides
    @SimpleDateFormatter
    fun provideSimpleDateFormatter() = SimpleDateFormat(Constants.DATE_FORMAT1, Locale.US)

    @Provides
    @DefaultDateTimeFormatter
    fun provideDefaultDateTimeFormatter() = SimpleDateFormat(Constants.DATE_TIME_FORMAT1, Locale.US)
}
