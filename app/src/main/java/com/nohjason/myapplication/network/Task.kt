package com.nohjason.myapplication.network

data class Task(
    val id: Int? = null,
    val name: String,
    val importanceEnum: String,
    val colorEnum: String,
    val date: String,
    val status: String? = null
)

data class ApiResponseTask(
    val status: Int,
    val message: String,
    val data: List<Task>
)