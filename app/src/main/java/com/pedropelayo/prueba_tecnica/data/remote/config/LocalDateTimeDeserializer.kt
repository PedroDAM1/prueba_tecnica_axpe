package com.pedropelayo.prueba_tecnica.data.remote.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        val dateString = json?.asString
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.parse(dateString, formatter)
    }
}