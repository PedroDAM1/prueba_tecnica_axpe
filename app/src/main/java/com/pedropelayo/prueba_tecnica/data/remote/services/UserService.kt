package com.pedropelayo.prueba_tecnica.data.remote.services

import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("1.4/")
    suspend fun getUsers(
        @Query("page") page : Int,
        @Query("seed") seed: String = DefaultParams.SEED,
        @Query("results") nResults : Int = DefaultParams.MAX_NUMBER_RESULTS
    ) : Response<ApiResponseInfo>

}