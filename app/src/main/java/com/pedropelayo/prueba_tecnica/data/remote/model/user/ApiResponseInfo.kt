package com.pedropelayo.prueba_tecnica.data.remote.model.user

import com.google.gson.annotations.SerializedName

data class ApiResponseInfo (
    @SerializedName("results")
    val results : List<UserApiModel>
)