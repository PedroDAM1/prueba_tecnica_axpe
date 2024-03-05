package com.pedropelayo.prueba_tecnica.data.local.di

import com.pedropelayo.prueba_tecnica.data.local.UserCache
import com.pedropelayo.prueba_tecnica.data.local.UserCacheImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideUserCache() : UserCache{
        return UserCacheImpl()
    }

}