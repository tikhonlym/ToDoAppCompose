package com.todo.app.todoappcompose.data.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Responsible for retrofir assembly */

object RetrofitFactory {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private var gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()

    private val client = OkHttpClient().newBuilder()
        .hostnameVerifier { _, _ -> true }
        .addInterceptor(Interceptor { chain ->
            val request: Request = chain.request()
            var response: Response = chain.proceed(request)
            if (!response.isSuccessful) {
                response.close()
                response = chain.proceed(request)
            }
            response
        })
        .addInterceptor(loggingInterceptor)
        .addInterceptor {
            val original = it.request()
            val request = original.newBuilder()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Generate-Fails:", "0")
                .header("Authorization", "Bearer ...")
            it.proceed(request.build())
        }
        .build()

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}