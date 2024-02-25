package com.pedropelayo.prueba_tecnica.data.remote.di

import com.google.gson.GsonBuilder
import com.pedropelayo.prueba_tecnica.data.remote.config.LocalDateTimeDeserializer
import com.pedropelayo.prueba_tecnica.data.remote.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient ) : Retrofit{
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit : Retrofit) : UserService {
        return retrofit.create(UserService::class.java)
    }

}