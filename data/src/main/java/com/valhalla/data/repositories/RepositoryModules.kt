package com.valhalla.data.repositories

import com.valhalla.data.repositories.post.PostRepository
import com.valhalla.data.repositories.post.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModules {

    @Binds
    @Singleton
    abstract fun bindPostRepository(repository: PostRepositoryImpl): PostRepository
}
