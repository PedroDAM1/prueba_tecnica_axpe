package com.pedropelayo.prueba_tecnica.domain.model

data class PictureUrlModel (
    val large : String? = null,
    val medium : String? = null,
    val thumbnail : String? = null,
    //Por defecto intentará recuperar la primera imagen de mayor resolucion o devolverá nulo
    val defaultImage : String? = large ?: medium ?: thumbnail
)