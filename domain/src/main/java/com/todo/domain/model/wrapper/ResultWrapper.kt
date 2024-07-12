package com.todo.domain.model.wrapper

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data object UnSynchronizedDataError : ResultWrapper<Nothing>()
    data object AuthorizationError : ResultWrapper<Nothing>()
    data object NoSuchDataError : ResultWrapper<Nothing>()
    data class UnknownSeverError(val code: Int) : ResultWrapper<Nothing>()
    data object NetworkError : ResultWrapper<Nothing>()
    data class GenericError(val code: Int? = null, val message: String? = null) :
        ResultWrapper<Nothing>()
}