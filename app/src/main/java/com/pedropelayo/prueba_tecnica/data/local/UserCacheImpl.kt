package com.pedropelayo.prueba_tecnica.data.local

import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import javax.inject.Inject


class UserCacheImpl @Inject constructor() : UserCache {

    private val userCache = mutableSetOf<UserModel>()

    override suspend fun getUsers(): Set<UserModel> = userCache

    override suspend fun saveUserData(data: Iterable<UserModel>) {
        userCache.addAll(data)
    }
}