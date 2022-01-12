package com.valhalla.appmodules

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.valhalla.BuildConfig
import com.valhalla.CACHE_SIZE
import com.valhalla.OKHTTP_CONNECT_TIMEOUT_SECONDS
import com.valhalla.OKHTTP_READ_TIMEOUT_SECONDS
import com.valhalla.OKHTTP_WRITE_TIMEOUT_SECONDS
import com.valhalla.data.services.ApiService
import com.valhalla.network.ServiceInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModules {

    @Provides
    @Singleton
    fun provideServiceInterceptor(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ) = ServiceInterceptor(context, sharedPreferences)

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context) =
        Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        @ApplicationContext context: Context,
        serviceInterceptor: ServiceInterceptor
    ) =
        OkHttpClient.Builder().apply {
            cache(cache)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addInterceptor(ChuckerInterceptor(context))
            }
            addInterceptor(serviceInterceptor)
            connectTimeout(OKHTTP_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(OKHTTP_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(OKHTTP_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(
                BuildConfig.BASE_URL +
                        if (BuildConfig.API_VERSION.isNotEmpty()) "${BuildConfig.API_VERSION}/"
                        else ""
            )
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create(ApiService::class.java)
}
