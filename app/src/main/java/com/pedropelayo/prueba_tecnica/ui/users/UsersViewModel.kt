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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
    //Detectara si estamos filtrando o no para cargar mas elementos
    var isFiltering : Boolean by mutableStateOf(false)

    private fun loadData(){
        viewModelScope.launch {
            userRepository.getUsers(pageIndex).collect{ result ->
                handleResponse(
                    response = result,
                    onError = { handleErrorResponse(it) },
                    onSuccess = { handleSuccesResponse(it) }
                )
            }
        }
    }


    fun loadMoreItem(){
        isLoading = true
        pageIndex++
        loadData()
    }

    fun searchByEmail(value: String) {
        applyFilter(
            userRepository.searchByEmail(value),
            value
        )
    }

    fun searchByName(value: String) {
        applyFilter(
            userRepository.searchByName(value),
            value
        )
    }

    private fun applyFilter(
        flow : Flow<DataResponse<List<UserModel>>>,
        value : String
    ){
        if(value.isEmpty()){
            isFiltering = false
            loadMoreItem()
            return
        }
        viewModelScope.launch {
            isFiltering = true
            flow.collect{ response ->
                handleResponse(
                    response = response,
                    onError = { handleErrorResponse(it) },
                    onSuccess = { handleSuccessFilter(it) }
                )
            }
        }
    }

    private fun handleResponse(
        response : DataResponse<List<UserModel>>,
        onSuccess : (List<UserModel>) -> Unit,
        onError : (ErrorType) -> Unit,
    ){
        when(response) {
            is DataResponse.Error -> onError(response.errorType)
            is DataResponse.Success ->{
                onSuccess(response.data)
            }
        }
    }

    private fun handleSuccessFilter(data : List<UserModel>){
        isLoading = false
        userList = data
        userResult = UsersPaginatedState.Succes(userList)
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

}