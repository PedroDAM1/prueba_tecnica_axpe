package com.pedropelayo.prueba_tecnica.ui.users

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.model.ErrorType
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.domain.repositories.UserRepository
import com.pedropelayo.prueba_tecnica.ui.users.state.UsersPaginatedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

//    private val _userState : MutableStateFlow<DataResponse<List<>>>
    private var pageIndex : Int = 0
    private var userList : List<UserModel> = listOf()

    var userResult : UsersPaginatedState by mutableStateOf(UsersPaginatedState.Succes(userList))
    var isLoading : Boolean by mutableStateOf(true)

    private fun loadData(){
        viewModelScope.launch {
            userRepository.getUsers(pageIndex).collect{ result ->
                when(result) {
                    is DataResponse.Error -> handleErrorResponse(result.errorType)
                    is DataResponse.Success ->{
                        handleSuccesResponse(result.data)
                    }
                }
            }
        }
    }
    private fun handleSuccesResponse(data: List<UserModel>) {
        isLoading = false
        userList = userList.plus(data)
        userResult = UsersPaginatedState.Succes(userList)
    }

    private fun handleErrorResponse(error: ErrorType) {
        val errorMsg = when(error){
            ErrorType.NotFound -> "No se ha obtenido ningún resultado"
            is ErrorType.UnknowError -> "Ha ocurrido un error desconocido"
        }

        userResult = UsersPaginatedState.Error(errorMsg)
    }

    // TODO: REVISAR POR QUE ESTE METODO SE LLAMA 2 VECES SEGUIDAS EN LUGAR DE 1
    private var loadMoreItemsCount = 0

    fun loadMoreItem(){
        loadMoreItemsCount++
        isLoading = true
        pageIndex++
        loadData()
        Log.d("MORE ITEMS", loadMoreItemsCount.toString())
    }

    fun searchByEmail(value: String) {

    }

    fun searchByName(value: String) {

    }

}