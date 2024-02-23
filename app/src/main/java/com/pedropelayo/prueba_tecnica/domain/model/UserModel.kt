package com.pedropelayo.prueba_tecnica.domain.model

import java.time.LocalDateTime

data class UserModel(
    val firstName : String,
    val lastName : String,
    val email : String,
    val gender : GenderTypes,
    val registrationDate : LocalDateTime,
    val phone : String
)
