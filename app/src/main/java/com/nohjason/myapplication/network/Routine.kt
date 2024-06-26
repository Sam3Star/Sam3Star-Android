package com.nohjason.myapplication.network

data class Routine(
    val name: String,
    val importanceEnum: String,
    val colorEnum: String,
    val id: Int? = null,
    val state: String? = null
)

data class ApiResponseRoutine(
    val status: Int,
    val message: String,
    val data: List<Routine>
)