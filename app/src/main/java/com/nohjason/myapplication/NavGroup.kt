package com.nohjason.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.screen.add.RoutineAddScreen
import com.nohjason.myapplication.screen.MainScreen
import com.nohjason.myapplication.screen.SelectScreen
import com.nohjason.myapplication.screen.add.TaskAddScreen
import com.nohjason.myapplication.screen.update.TaskUpdateScreen
import com.nohjason.myapplication.screen.update.UpdateScreen

enum class Screen() {
    Main,
    AddRoutine,
    AddTask,
    RoutineUpdate,
    TaskUpdate,
}

@Composable
fun NavGroup(viewModel: MainViewModel){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.name
    ) {
        composable(route = Screen.Main.name) {
            MainScreen(navController, viewModel)
        }
        composable(route = Screen.AddRoutine.name) {
            RoutineAddScreen(navController, viewModel)
        }
        composable(route = Screen.AddTask.name) {
            TaskAddScreen(navController, viewModel)
        }
        composable(route = "selectScreen") {
            SelectScreen(navController, viewModel)
        }
        composable(route = Screen.RoutineUpdate.name+"/{id}/{name}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("id")
            val name = backStackEntry.arguments?.getString("name")
            UpdateScreen(
                taskId = (taskId?:0).toString(),
                name?:"",
                navController,
                viewModel
            )
        }
        composable(route = Screen.TaskUpdate.name+"/{id}/{name}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("id")
            val name = backStackEntry.arguments?.getString("name")
            TaskUpdateScreen(
                taskId = (taskId?:0).toString(),
                name?:"",
                navController,
                viewModel
            )
        }
    }
}