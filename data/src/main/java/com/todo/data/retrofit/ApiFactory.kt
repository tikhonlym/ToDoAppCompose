package com.todo.data.retrofit

import com.todo.data.repository.task.TaskApiCalls
import retrofit2.create

object ApiFactory {
    val todoApi: TaskApiCalls =
        RetrofitFactory.retrofit(baseUrl = "https://hive.mrdekk.ru/todo/")
            .create()
}