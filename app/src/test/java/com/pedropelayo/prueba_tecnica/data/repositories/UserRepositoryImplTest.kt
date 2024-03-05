package com.pedropelayo.prueba_tecnica.data.repositories

import com.pedropelayo.prueba_tecnica.DataFaker
import com.pedropelayo.prueba_tecnica.data.local.UserCache
import com.pedropelayo.prueba_tecnica.data.remote.mapper.ResponseMapper
import com.pedropelayo.prueba_tecnica.data.remote.mapper.UserMapper
import com.pedropelayo.prueba_tecnica.data.remote.model.user.ApiResponseInfo
import com.pedropelayo.prueba_tecnica.data.remote.services.UserService
import com.pedropelayo.prueba_tecnica.domain.model.DataResponse
import com.pedropelayo.prueba_tecnica.domain.model.ErrorType
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.domain.repositories.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepository

    @RelaxedMockK
    private lateinit var service: UserService
    @RelaxedMockK
    private lateinit var responseMapper : ResponseMapper<ApiResponseInfo>
    @RelaxedMockK
    private lateinit var userMapper : UserMapper
    @RelaxedMockK
    private lateinit var userCache : UserCache

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.Unconfined

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        userRepository = UserRepositoryImpl(service, responseMapper, userMapper, userCache, ioDispatcher)
    }

    @Test
    fun `when get users with a correct page should be returns a user success results`() = runBlocking {
        // Given
        val responseApi = DataFaker.apiResponseInfoFake
        val correctResponse = DataResponse.Success(listOf(DataFaker.userModelFake))
        val page = 1
        coEvery { service.getUsers(page) } returns Response.success(responseApi)
        every { responseMapper.map(any()) } returns DataResponse.Success(responseApi)
        every { userMapper.map(any()) } returns DataFaker.userModelFake
        // When
        val result = userRepository.getUsers(page).first()  // Usar toList para forzar la ejecución de la flow

        // Then
        coVerify(exactly = 1) { service.getUsers(page) }
        verify(exactly = 1) { responseMapper.map(any()) }
        assertEquals(correctResponse,result)
    }

    @Test
    fun `when id is correct and user is cached then returns a success response with user model`() = runBlocking {
        //Given
        val uuid = "7555af32-0598-422b-aacc-a52481f6c3b4"
        val correctResponse = DataResponse.Success(DataFaker.userModelFake)
        coEvery { userCache.getUsers() } returns setOf(DataFaker.userModelFake)

        //When
        val result = userRepository.getUserByUuid(uuid).first()

        //Then
        coVerify(exactly = 1) { userCache.getUsers() }
        assertEquals(correctResponse,result)
    }

    @Test
    fun `when id not exists then returns a error not found`() = runBlocking{
        //Given
        val uuid = "asdasd"
        val correctResponse = DataResponse.Error<UserModel>(ErrorType.NotFound)
        coEvery { userCache.getUsers() } returns setOf()

        //When
        val result = userRepository.getUserByUuid(uuid).first()

        //Then
        coVerify(exactly = 1) { userCache.getUsers() }
        assertEquals(correctResponse, result)
    }

    @Test
    fun `when search by name and name exists in cache then should returns a correct result for at least one item `() = runBlocking {
        //Given
        val name = "jael"
        val correctResponse = DataResponse.Success(listOf(DataFaker.userModelFake))
        coEvery { userCache.getUsers() } returns setOf(DataFaker.userModelFake)

        //When
        val result = userRepository.searchByName(name).first()

        //Then
        coVerify(exactly = 1) { userCache.getUsers() }
        assertEquals(correctResponse,result)
    }

    @Test
    fun `when search by name and name not exists in cache then should returns a not found response`() = runBlocking {
        //Given
        val name = "not exists"
        val correctResponse = DataResponse.Error<List<UserModel>>(ErrorType.NotFound)
        coEvery { userCache.getUsers() } returns setOf(DataFaker.userModelFake)

        //When
        val result = userRepository.searchByName(name).first()

        //Then
        coVerify(exactly = 1) { userCache.getUsers() }
        assertEquals(correctResponse,result)
    }


    @Test
    fun `when search by email and email exists in cache then should returns a correct result for at least one item `() = runBlocking {
        //Given
        val email = "jael.g"
        val correctResponse = DataResponse.Success(listOf(DataFaker.userModelFake))
        coEvery { userCache.getUsers() } returns setOf(DataFaker.userModelFake)

        //When
        val result = userRepository.searchByEmail(email).first()

        //Then
        coVerify(exactly = 1) { userCache.getUsers() }
        assertEquals(correctResponse,result)
    }

    @Test
    fun `when search by email and email not exists in cache then should returns a not found response`() = runBlocking {
        //Given
        val email = "not exists"
        val correctResponse = DataResponse.Error<List<UserModel>>(ErrorType.NotFound)
        coEvery { userCache.getUsers() } returns setOf(DataFaker.userModelFake)

        //When
        val result = userRepository.searchByEmail(email).first()

        //Then
        coVerify(exactly = 1) { userCache.getUsers() }
        assertEquals(correctResponse,result)
    }
}