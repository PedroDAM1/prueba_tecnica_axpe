package com.pedropelayo.prueba_tecnica.domain.di

import com.pedropelayo.prueba_tecnica.data.utils.AppDispatchers
import com.pedropelayo.prueba_tecnica.data.utils.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    @Dispatcher(AppDispatchers.IO)
    fun provideIoDispatcher() : CoroutineDispatcher{
        return Dispatchers.IO
    }

}