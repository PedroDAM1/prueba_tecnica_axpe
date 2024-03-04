package com.pedropelayo.prueba_tecnica.ui.user_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.model.ErrorType
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.domain.repositories.UserRepository
import com.pedropelayo.prueba_tecnica.ui.user_detail.state.UserDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var state: UserDetailState by mutableStateOf(UserDetailState.Loading)

    fun loadUser(uuid : String){
        viewModelScope.launch {
            state = UserDetailState.Loading
            repository.getUserByUuid(uuid).collect{ response ->
                when (response) {
                    is DataResponse.Error -> handleErrorState(response.errorType)
                    is DataResponse.Success -> handleSuccessState(response.data)
                }
            }
        }
    }

    private fun handleSuccessState(data: UserModel) {
        state = UserDetailState.Success(data)
    }

    private fun handleErrorState(error: ErrorType) {
        val message = when (error) {
            ErrorType.NotFound -> "No se ha podido encontrar el usuario"
            is ErrorType.UnknowError -> "Ocurrrió un error desconocido"
        }

        state = UserDetailState.Error(message)
    }

}