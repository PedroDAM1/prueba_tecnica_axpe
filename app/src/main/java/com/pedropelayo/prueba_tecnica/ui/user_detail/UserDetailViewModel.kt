package com.pedropelayo.prueba_tecnica.ui.user_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pedropelayo.prueba_tecnica.ui.user_detail.state.UserDetailState

class UserDetailViewModel : ViewModel() {

    var state: UserDetailState by mutableStateOf(UserDetailState.Loading)

    fun loadUser(uuid : String){

    }

}