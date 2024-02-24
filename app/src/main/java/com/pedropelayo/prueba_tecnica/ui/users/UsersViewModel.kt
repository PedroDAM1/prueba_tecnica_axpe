package com.pedropelayo.prueba_tecnica.ui.users

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.repositories.UserRepository
import com.pedropelayo.prueba_tecnica.ui.users.state.UsersPaginatedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

//    private val _userState : MutableStateFlow<DataResponse<List<>>>
    private var pageIndex : Int = 1
    private val _state : MutableStateFlow<UsersPaginatedState> = MutableStateFlow(UsersPaginatedState.Loading)
    val state : StateFlow<UsersPaginatedState> = _state

    fun initLoad(){

    }

    fun loadMoreItem(){


    }

    init {
        viewModelScope.launch {
            userRepository.getUsers(1).collect{ response ->
                when(response){
                    is DataResponse.Error -> Log.d("REPOSITORY", "Error")
                    is DataResponse.Success -> {
                        Log.d("REPOSITORY", response.data.toString())
                    }
                }
            }

        }
    }

}