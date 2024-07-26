package com.todo.data

import com.todo.data.repository.BaseRepository
import com.todo.domain.model.wrapper.ResultWrapper
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class BaseRepositoryUnitTest {

    @Test
    fun `safeAPICall returns Success on 200 response`() = runTest {
        val mockResponse: Response<String> = mockk {
            every { isSuccessful } returns true
            every { code() } returns 200
            every { body() } returns "Success"
        }
        val apiCall = suspend { mockResponse }
        val result = BaseRepository.safeAPICall(kotlinx.coroutines.Dispatchers.Unconfined, apiCall)

        TestCase.assertTrue(result is ResultWrapper.Success && (result.value == "Success"))
    }

    @Test
    fun `safeAPICall handles different HTTP errors`() = runTest {
        listOf(400, 401, 404).forEach { statusCode ->
            val mockResponse: Response<String> = mockk {
                every { isSuccessful } returns false
                every { code() } returns statusCode
            }
            val apiCall = suspend { mockResponse }
            val result =
                BaseRepository.safeAPICall(kotlinx.coroutines.Dispatchers.Unconfined, apiCall)

            assertTrue(
                when (statusCode) {
                    400 -> result is ResultWrapper.UnSynchronizedDataError
                    401 -> result is ResultWrapper.AuthorizationError
                    404 -> result is ResultWrapper.NoSuchDataError
                    else -> false
                }
            )
        }
    }
}