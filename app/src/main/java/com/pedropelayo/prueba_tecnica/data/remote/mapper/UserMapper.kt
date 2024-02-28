package com.pedropelayo.prueba_tecnica.data.remote.mapper

import com.pedropelayo.prueba_tecnica.data.remote.model.user.UserApiModel
import com.pedropelayo.prueba_tecnica.domain.model.GenderTypes
import com.pedropelayo.prueba_tecnica.domain.model.PictureUrlModel
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.domain.utils.mappers.Mapper

class UserMapper : Mapper<UserApiModel, UserModel> {
    override fun map(item: UserApiModel): UserModel {
        val gender : GenderTypes =
            when(item.gender){
                "female" -> {GenderTypes.Female}
                "male" -> {GenderTypes.Male}
                else -> {GenderTypes.NotDefined}
            }

        return UserModel(
            firstName = item.name.firstName,
            lastName = item.name.lastName,
            email = item.email,
            gender = gender,
            registrationDate = item.registrationDate.date,
            phone = item.phone,
            picture = PictureUrlModel(
                large = item.picture.large,
                medium = item.picture.medium,
                thumbnail = item.picture.thumbnail
            ),
            uuid = item.login.uuid
        )
    }
}