package com.pedropelayo.prueba_tecnica.data.repositories

import com.pedropelayo.prueba_tecnica.data.remote.mapper.ResponseMapper
import com.pedropelayo.prueba_tecnica.data.remote.mapper.UserMapper
import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
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
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val service : UserService,
    private val responseMapper : ResponseMapper<ApiResponseInfo>,
    private val userMapper : UserMapper,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher : CoroutineDispatcher
) : UserRepository {


    override fun getUsers(page: Int): Flow<DataResponse<List<UserModel>>> = flow {
        when(val response = fetchUsers(page)){
            is DataResponse.Success -> {
                //Si la api devuelve una respuesta correcta, la mmapeamos a nuestra capa de dominio
                val usersDomain = response.data.results.map { userMapper.map(it) }
                emit(DataResponse.Success(usersDomain))
            }
            //Si la api devuelve un error lo emitiremmos de vuelta
            is DataResponse.Error -> emit(DataResponse.Error(response.errorType))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Hace un fetching de datos a la api y devuelve un wrapper de la respuesta
     *
     * @param page
     * @return
     */
    private suspend fun fetchUsers(page : Int) : DataResponse<ApiResponseInfo> {
        val response = service.getUsers(page)
        return responseMapper.map(response)
        //return DataResponse.Error(ErrorType.NotFound)
    }

}