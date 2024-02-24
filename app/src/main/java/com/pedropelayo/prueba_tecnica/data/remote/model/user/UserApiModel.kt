package com.pedropelayo.prueba_tecnica.data.remote.model.user

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class UserApiModel(
    @SerializedName("name")
    val name : NameUser,
    @SerializedName("email")
    val email : String,
    @SerializedName("gender")
    val gender : String,
    @SerializedName("registered")
    val registrationDate : RegisterDate,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("picture")
    val picture: PictureUser
)

data class NameUser(
    @SerializedName("first")
    val firstName : String,
    @SerializedName("last")
    val lastName : String
)

data class RegisterDate(
    @SerializedName("date")
    val date : LocalDateTime
)

data class PictureUser(
    @SerializedName("large")
    val large : String,
    @SerializedName("medium")
    val medium : String,
    @SerializedName("thumbnail")
    val thumbnail : String
)
