package com.pedropelayo.prueba_tecnica.domain.di

import com.pedropelayo.prueba_tecnica.data.local.UserCache
import com.pedropelayo.prueba_tecnica.data.remote.mapper.ResponseMapper
import com.pedropelayo.prueba_tecnica.data.remote.mapper.UserMapper
import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
import com.pedropelayo.prueba_tecnica.data.remote.services.UserService
import com.pedropelayo.prueba_tecnica.data.repositories.UserRepositoryImpl
import com.pedropelayo.prueba_tecnica.data.utils.AppDispatchers
import com.pedropelayo.prueba_tecnica.data.utils.Dispatcher
import com.pedropelayo.prueba_tecnica.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideUsersRepository(
        userService: UserService,
        responseMapper: ResponseMapper<ApiResponseInfo>,
        userMapper: UserMapper,
        userCache: UserCache,
        @Dispatcher(AppDispatchers.IO) ioDispatcher : CoroutineDispatcher
    ) : UserRepository {
        //return UserRepositoryImpl(userService, ioDispatcher)
        return UserRepositoryImpl(userService, responseMapper, userMapper, userCache, ioDispatcher)
    }

}