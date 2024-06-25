package com.nohjason.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.screen.RoutineAddScreen
import com.nohjason.myapplication.screen.MainScreen
import com.nohjason.myapplication.screen.UpdateScreen

enum class Screen() {
    Main,
    Add,
    Update
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
        composable(route = Screen.Add.name) {
            RoutineAddScreen(navController, viewModel)
        }
        composable(route = Screen.Update.name+"/{id}/{name}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("id")
            val name = backStackEntry.arguments?.getString("name")
            UpdateScreen(
                taskId = (taskId?:0).toString(),
                name?:"",
                navController,
                viewModel
            )
        }
    }
}