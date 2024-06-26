package com.nohjason.myapplication.network

import android.accessibilityservice.AccessibilityService.TakeScreenshotCallback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("routine")
    suspend fun createRoutine(@Body routine: Routine)

    @GET("routine") // 엔드포인트는 실제 API URL에 맞게 변경
    suspend fun getRoutine(): ApiResponseRoutine

    @DELETE("routine/{id}")
    suspend fun deleteRoutine(@Path("id") id: Int): Response<Void>

    @PATCH("routine/{id}")
    suspend fun updateRoutine(
        @Path("id") id: Int,
        @Body routine: Routine
    ): Response<Routine>

    @PATCH("routine/set/{id}")
    suspend fun updateSetRoutine(
        @Path("id") id: Int,
        @Body routine: Routine
    ): Response<Routine>


    @POST("task")
    suspend fun createTask(@Body task: Task)

    @GET("/task/{date}")
    suspend fun getTasksDate(@Path("date") date: String): Response<ApiResponseTask>

    @DELETE("/task/del/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Void>

    @PATCH("/task/edit/{id}")
    suspend fun updateTask(
        @Path("id") id: Int,
        @Body task: Task
    ): Response<Void>

    @PATCH("/task/{id}")
    suspend fun updateSetTask(
        @Path("id") id: Int,
    ): Response<Void>
}