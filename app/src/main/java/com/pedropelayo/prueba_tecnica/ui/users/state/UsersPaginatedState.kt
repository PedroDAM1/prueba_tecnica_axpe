package com.pedropelayo.prueba_tecnica.ui.users.state

import com.pedropelayo.prueba_tecnica.domain.model.UserModel

sealed class UsersPaginatedState {

    data class Succes(val data : List<UserModel>) : UsersPaginatedState()
    data class Error(val message : String) : UsersPaginatedState()

}