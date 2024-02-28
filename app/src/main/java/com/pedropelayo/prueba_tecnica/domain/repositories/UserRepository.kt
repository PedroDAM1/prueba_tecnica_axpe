package com.pedropelayo.prueba_tecnica.domain.repositories

import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(page : Int) : Flow<DataResponse<List<UserModel>>>
    fun searchByName(name : String) : Flow<DataResponse<List<UserModel>>>
    fun searchByEmail(email : String) : Flow<DataResponse<List<UserModel>>>

}