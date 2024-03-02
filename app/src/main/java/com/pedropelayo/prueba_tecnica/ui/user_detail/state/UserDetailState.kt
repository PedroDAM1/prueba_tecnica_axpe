package com.pedropelayo.prueba_tecnica.ui.user_detail.state

import com.pedropelayo.prueba_tecnica.domain.model.UserModel

sealed class UserDetailState {

    data object Loading : UserDetailState()

    data class Success(val user : UserModel) : UserDetailState()

    data class Error(val message : String) : UserDetailState()

}