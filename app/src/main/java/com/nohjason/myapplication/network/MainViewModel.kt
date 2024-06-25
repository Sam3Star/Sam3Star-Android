package com.nohjason.myapplication.network

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel : ViewModel() {

    fun createTask(task: Task) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.createTask(task)
                Log.d("MainViewModel", "Task created successfully")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error creating task", e)
            }
        }
    }

    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    val tasks: State<List<Task>> get() = _tasks

    fun fetchTasks() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { RetrofitInstance.api.getTasks() }
                if (response.status == 200) {
                    _tasks.value = response.data
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            try {
                val response: Response<Void> = RetrofitInstance.api.deleteTask(id)
                if (response.isSuccessful) {
                    Log.d("MainViewModel", "Task deleted successfully: $id")
                    // 태스크 삭제 후 목록을 다시 가져옴
                    fetchTasks()
                } else {
                    Log.e("MainViewModel", "Error deleting task: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception deleting task", e)
            }
        }
    }

    fun updateTask(id: Int, task: Task) {
        viewModelScope.launch {
            try {
                val response: Response<Task> = RetrofitInstance.api.updateTask(id, task)
                if (response.isSuccessful) {
                    Log.d("MainViewModel", "Task updated successfully: $id")
                    fetchTasks() // 업데이트 후 목록을 다시 불러옵니다.
                } else {
                    Log.e("MainViewModel", "Error updating task: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception updating task", e)
            }
        }
    }

    fun updateRoutine(id: Int, task: Task) {
        viewModelScope.launch {
            try {
                val response: Response<Task> = RetrofitInstance.api.updateSet(id, task)
                if (response.isSuccessful) {
                    Log.d("MainViewModel", "Routine updated successfully: $id")
                    fetchTasks() // 업데이트 후 목록을 다시 불러옵니다.
                } else {
                    Log.e("MainViewModel", "Error updating routine: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Exception updating routine", e)
            }
        }
    }
}