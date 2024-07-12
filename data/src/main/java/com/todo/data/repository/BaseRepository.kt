package com.todo.data.repository

import com.todo.domain.model.wrapper.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Response

/** The base repository that makes a call to the network and maps errors */

object BaseRepository {
    suspend fun <T> safeAPICall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> Response<T>,
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful && response.code() == 200) {
                    ResultWrapper.Success(response.body()!!)
                } else {
                    when (response.code()) {
                        400 -> ResultWrapper.UnSynchronizedDataError
                        401 -> ResultWrapper.AuthorizationError
                        404 -> ResultWrapper.NoSuchDataError
                        else -> ResultWrapper.UnknownSeverError(response.code())
                    }
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is IOException -> {
                        ResultWrapper.NetworkError
                    }
                    else -> ResultWrapper.GenericError(message = throwable.message)
                }
            }
        }
    }
}