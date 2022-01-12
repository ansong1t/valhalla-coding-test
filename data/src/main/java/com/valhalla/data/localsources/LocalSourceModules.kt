package com.valhalla.data.localsources

import com.valhalla.data.localsources.post.PostLocalSource
import com.valhalla.data.localsources.post.PostLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalSourceModules {

    @Binds
    @Singleton
    abstract fun bindWalletLocalSource(source: PostLocalSourceImpl): PostLocalSource
}
