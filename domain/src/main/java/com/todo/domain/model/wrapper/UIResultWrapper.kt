package com.todo.domain.model.wrapper

sealed class UIResultWrapper<out T> {
    data class Success<out T>(val value: T) : UIResultWrapper<T>()
    data class SuccessSynchronizationNeeded<out T>(val value: T) : UIResultWrapper<T>()
    data class SuccessNoInternet<out T>(val value: T) : UIResultWrapper<T>()
    data class SuccessWithServerError<out T>(val value: T) : UIResultWrapper<T>()
}
