package com.pedropelayo.prueba_tecnica.data.repositories

import com.pedropelayo.prueba_tecnica.data.remote.mapper.ResponseMapper
import com.pedropelayo.prueba_tecnica.data.remote.mapper.UserMapper
import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
import com.pedropelayo.prueba_tecnica.data.remote.model.user.UserApiModel
import com.pedropelayo.prueba_tecnica.data.remote.services.UserService
import com.pedropelayo.prueba_tecnica.data.utils.AppDispatchers
import com.pedropelayo.prueba_tecnica.data.utils.Dispatcher
import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val service : UserService,
    private val responseMapper : ResponseMapper<ApiResponseInfo>,
    private val userMapper : UserMapper,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : UserRepository {

    private val userCache = mutableSetOf<UserModel>()

    override fun getUsers(page: Int): Flow<DataResponse<List<UserModel>>> = flow {
        val responseApi = fetchUsers(page)
        emit(mapUsers(responseApi))
    }.flowOn(Dispatchers.IO)

    private fun mapUsers(responseApi : DataResponse<ApiResponseInfo>) : DataResponse<List<UserModel>>{
        return when(responseApi){
            is DataResponse.Success -> {
                //Si la api devuelve una respuesta correcta, la mmapeamos a nuestra capa de dominio
                val domainList = responseApi.data.results.mapNotNull { userApi ->
                    val userDomain = userMapper.map(userApi)
                    //Si el usuario ya lo hemos recuperado, entonces  no lo volvemos a devolver
                    if(userCache.contains(userDomain)) null
                    else userDomain
                }
                DataResponse.Success(domainList)
            }
            //Si devuelve una respuesta incorrecta la retornamos
            is DataResponse.Error -> DataResponse.Error(responseApi.errorType)
        }
    }

    /**
     * Hace un fetching de datos a la api y devuelve un wrapper de la respuesta
     *
     * @param page
     * @return
     */
    private suspend fun fetchUsers(page : Int) : DataResponse<ApiResponseInfo> = withContext(ioDispatcher) {
        val response = service.getUsers(page)
        responseMapper.map(response)
        //return DataResponse.Error(ErrorType.NotFound)
    }

}