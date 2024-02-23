package com.pedropelayo.prueba_tecnica.domain.model

sealed class DataResponse<T> {

    data class Success<T>(val data : T) : DataResponse<T>()

    data class Error<T>(val errorType : ErrorType) : DataResponse<T>()

}

sealed class ErrorType{

    data object NotFound : ErrorType()

    data class UnknowError(val message : String = "", val innerEx : Exception? = null) : ErrorType()
}