package com.pedropelayo.prueba_tecnica.data.repositories

import com.google.gson.GsonBuilder
import com.pedropelayo.prueba_tecnica.data.remote.config.LocalDateTimeDeserializer
import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
import com.pedropelayo.prueba_tecnica.data.remote.model.user.Login
import com.pedropelayo.prueba_tecnica.data.remote.model.user.NameUser
import com.pedropelayo.prueba_tecnica.data.remote.model.user.PictureUser
import com.pedropelayo.prueba_tecnica.data.remote.model.user.RegisterDate
import com.pedropelayo.prueba_tecnica.data.remote.model.user.UserApiModel
import com.pedropelayo.prueba_tecnica.domain.model.GenderTypes
import com.pedropelayo.prueba_tecnica.domain.model.PictureUrlModel
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DataFaker {

    private const val json = """
{
    "results": [
        {
            "gender": "female",
            "name": {
                "title": "Mademoiselle",
                "first": "Jael",
                "last": "Gauthier"
            },
            "location": {
                "street": {
                    "number": 6828,
                    "name": "Rue Saint-Georges"
                },
                "city": "Dallenwil",
                "state": "Basel-Stadt",
                "country": "Switzerland",
                "postcode": 9719,
                "coordinates": {
                    "latitude": "-60.7993",
                    "longitude": "72.9136"
                },
                "timezone": {
                    "offset": "-3:00",
                    "description": "Brazil, Buenos Aires, Georgetown"
                }
            },
            "email": "jael.gauthier@example.com",
            "login": {
                "uuid": "7555af32-0598-422b-aacc-a52481f6c3b4",
                "username": "silverostrich414",
                "password": "shop",
                "salt": "uLFkkTOQ",
                "md5": "90295edb5b9bcaeafd3b1adfe4a7d2eb",
                "sha1": "fef8918d1c4c12dc2280557e32fa8a6d4119b825",
                "sha256": "8f7fd2217e3e25be05cf81cea5cdc6da9e917b32c49297aca26623dfc94edf9c"
            },
            "dob": {
                "date": "1953-03-02T20:45:15.917Z",
                "age": 71
            },
            "registered": {
                "date": "2008-05-21T04:04:28.884Z",
                "age": 15
            },
            "phone": "077 727 25 53",
            "cell": "076 898 35 57",
            "id": {
                "name": "AVS",
                "value": "756.3039.3530.19"
            },
            "picture": {
                "large": "https://randomuser.me/api/portraits/women/52.jpg",
                "medium": "https://randomuser.me/api/portraits/med/women/52.jpg",
                "thumbnail": "https://randomuser.me/api/portraits/thumb/women/52.jpg"
            },
            "nat": "CH"
        }
    ],
    "info": {
        "seed": "SEMILLA_GEN",
        "results": 2,
        "page": 1,
        "version": "1.4"
    }
}"""

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
        .create()
    val apiResponseInfoFromGson: ApiResponseInfo = gson.fromJson(json, ApiResponseInfo::class.java)

    val userApiModelFake = UserApiModel(
        name = NameUser(
            firstName = "Jael",
            lastName = "Gauthier"
        ),
        email = "jael.gauthier@example.com",
        gender = "female",
        registrationDate = RegisterDate(
            date = LocalDateTime.parse("2008-05-21T04:04:28.884Z", dateTimeFormatter)
        ),
        phone = "077 727 25 53",
        picture = PictureUser(
            large = "https://randomuser.me/api/portraits/women/52.jpg",
            medium = "https://randomuser.me/api/portraits/med/women/52.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/women/52.jpg"
        ),
        login = Login(
            "7555af32-0598-422b-aacc-a52481f6c3b4"
        )
    )

    val apiResponseInfoFake = ApiResponseInfo(results = listOf(userApiModelFake))

    val userModelFake = UserModel(
        firstName = "Jael",
        lastName = "Gauthier",
        email = "jael.gauthier@example.com",
        gender = GenderTypes.Female,
        registrationDate = LocalDateTime.parse("2008-05-21T04:04:28.884Z", dateTimeFormatter),
        phone = "077 727 25 53",
        picture = PictureUrlModel(
            large = "https://randomuser.me/api/portraits/women/52.jpg",
            medium = "https://randomuser.me/api/portraits/med/women/52.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/women/52.jpg"
        ),
        uuid = "7555af32-0598-422b-aacc-a52481f6c3b4"
    )
}