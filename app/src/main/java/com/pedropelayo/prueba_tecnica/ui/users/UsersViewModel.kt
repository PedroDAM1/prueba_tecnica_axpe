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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

//    private val _userState : MutableStateFlow<DataResponse<List<>>>
    private var pageIndex : Int = 1
    var userResult : UsersPaginatedState by mutableStateOf(UsersPaginatedState.Succes(listOf()))
    var isLoading : Boolean by mutableStateOf(true)

    fun initLoad(){
        viewModelScope.launch {
            userRepository.getUsers(pageIndex).collect{ result ->
                when(result) {
                    is DataResponse.Error -> handleErrorResponse(result.errorType)
                    is DataResponse.Success -> handleErrorSucces(result.data)
                }
            }
        }
    }

    private fun handleErrorSucces(data: List<UserModel>) {
        userResult = UsersPaginatedState.Succes(data)
    }

    private fun handleErrorResponse(error: ErrorType) {
        val errorMsg = when(error){
            ErrorType.NotFound -> "No se ha obtenido ningún resultado"
            is ErrorType.UnknowError -> "Ha ocurrido un error desconocido"
        }

        userResult = UsersPaginatedState.Error(errorMsg)
    }

    fun loadMoreItem(){


    }

    init {
        initLoad()
    }

}