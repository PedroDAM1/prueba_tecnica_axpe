package com.pedropelayo.prueba_tecnica.data.remote.di

import com.pedropelayo.prueba_tecnica.data.remote.mapper.ResponseMapper
import com.pedropelayo.prueba_tecnica.data.remote.mapper.UserMapper
import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MappersModule {

    @Provides
    @Singleton
    fun provideUserMapper() : UserMapper{
        return UserMapper()
    }

    @Provides
    @Singleton
    fun provideApiResponseMapper() : ResponseMapper<ApiResponseInfo>{
        return ResponseMapper()
    }

}