package com.pedropelayo.prueba_tecnica.data.local

import com.pedropelayo.prueba_tecnica.domain.model.UserModel

interface UserCache {

    suspend fun getUsers() : Set<UserModel>

    suspend fun saveUserData(data : Iterable<UserModel>)

}