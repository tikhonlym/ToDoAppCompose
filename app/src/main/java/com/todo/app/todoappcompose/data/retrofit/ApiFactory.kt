package com.todo.app.todoappcompose.data.retrofit

import com.todo.app.todoappcompose.data.repository.TodoApiCalls
import retrofit2.create

object ApiFactory {
    val todoApi: TodoApiCalls =
        RetrofitFactory.retrofit(baseUrl = "https://hive.mrdekk.ru/todo/")
            .create()
}