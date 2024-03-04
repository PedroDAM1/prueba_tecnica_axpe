package com.pedropelayo.prueba_tecnica.domain.utils.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toLocalDateFormat() : String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.format(formatter)
}