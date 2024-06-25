package com.nohjason.myapplication.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("routine")
    suspend fun createTask(@Body task: Task)

    @GET("routine") // 엔드포인트는 실제 API URL에 맞게 변경
    suspend fun getTasks(): ApiResponse

    @DELETE("routine/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Void>

    @PATCH("routine/{id}")
    suspend fun updateTask(
        @Path("id") id: Int,
        @Body task: Task
    ): Response<Task>

    @PATCH("routine/set/{id}")
    suspend fun updateSet(
        @Path("id") id: Int,
        @Body task: Task
    ): Response<Task>
}