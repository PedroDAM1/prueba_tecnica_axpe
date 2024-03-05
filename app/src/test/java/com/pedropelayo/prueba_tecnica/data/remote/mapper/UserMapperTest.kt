package com.pedropelayo.prueba_tecnica.data.remote.mapper

import com.pedropelayo.prueba_tecnica.DataFaker
import io.mockk.MockKAnnotations
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserMapperTest{

    private lateinit var userMapper: UserMapper

    @Before
    fun obBefore(){
        MockKAnnotations.init(this)
        userMapper = UserMapper()
    }

    @Test
    fun `when a mapping a user from api then returns a user from domain`(){
        //Given
        val userApi = DataFaker.userApiModelFake
        val correctResult = DataFaker.userModelFake

        //When
        val result = userMapper.map(userApi)

        //then
        assertEquals(correctResult, result)
    }
}