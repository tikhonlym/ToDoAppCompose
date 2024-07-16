package com.todo.data.repository.task

import com.todo.domain.model.response.TaskListResponse
import com.todo.domain.model.response.TaskResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiCalls {

    @GET("list")
    fun getList(): Deferred<Response<TaskListResponse>>

    @GET("list/{id}")
    fun getTask(
        @Path("id") id: String,
    ): Deferred<Response<TaskResponse>>

    @DELETE("list/{id}")
    fun deleteTask(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
    ): Deferred<Response<TaskResponse>>

    @POST("list")
    fun createTask(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body data: UpdateTaskRequest,
    ): Deferred<Response<TaskResponse>>

    @PATCH("list")
    fun updateList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body data: UpdateListRequest,
    ): Deferred<Response<TaskListResponse>>

    @PUT("list/{id}")
    fun updateTask(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body task: UpdateTaskRequest,
    ): Deferred<Response<TaskResponse>>

}
