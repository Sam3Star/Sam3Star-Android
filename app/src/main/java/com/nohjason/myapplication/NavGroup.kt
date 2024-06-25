package com.nohjason.myapplication

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.screen.AddScreen
import com.nohjason.myapplication.screen.MainScreen

enum class Screen() {
    Main,
    Add,
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
            AddScreen(navController, viewModel)
        }
    }
}