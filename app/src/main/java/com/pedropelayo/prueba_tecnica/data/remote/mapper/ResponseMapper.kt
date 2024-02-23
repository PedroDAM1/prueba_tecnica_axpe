package com.pedropelayo.prueba_tecnica.data.remote.mapper

import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.model.ErrorType
import com.pedropelayo.prueba_tecnica.domain.utils.mappers.Mapper
import retrofit2.Response


class ResponseMapper<T> : Mapper<Response<T>, DataResponse<T>> {

    override fun map(item : Response<T>) : DataResponse<T>{
        val body = item.body()

        if(!item.isSuccessful || body == null){
            return DataResponse.Error(ErrorType.UnknowError("Hubo un problema al intentar recuparar los datos"))
        }
        return DataResponse.Success(body)
    }

}