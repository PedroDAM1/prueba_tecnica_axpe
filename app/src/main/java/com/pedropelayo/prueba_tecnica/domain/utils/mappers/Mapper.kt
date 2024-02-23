package com.pedropelayo.prueba_tecnica.domain.utils.mappers

interface Mapper<T, out R> {

    fun map(item : T) : R

}