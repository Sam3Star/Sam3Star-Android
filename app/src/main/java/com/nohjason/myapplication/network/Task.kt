package com.nohjason.myapplication.network

data class Task(
    val name: String,
    val importanceEnum: String,
    val colorEnum: String,
    val id: Int? = null
//    val startAt: String,
//    val endAt: String
)

data class ApiResponse(
    val status: Int,
    val message: String,
    val data: List<Task>
)