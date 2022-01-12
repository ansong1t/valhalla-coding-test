package com.valhalla.data.remotesources

import com.valhalla.data.remotesources.post.PostRemoteSource
import com.valhalla.data.remotesources.post.PostRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteSourceModules {

    @Binds
    @Singleton
    abstract fun bindPostRemoteSource(source: PostRemoteSourceImpl): PostRemoteSource
}
