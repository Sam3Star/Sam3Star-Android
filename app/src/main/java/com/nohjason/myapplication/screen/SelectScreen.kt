package com.nohjason.myapplication.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nohjason.myapplication.Screen
import com.nohjason.myapplication.network.MainViewModel

@Composable
fun SelectScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
   Column(
       Modifier
           .fillMaxSize()
           .padding(horizontal = 30.dp),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Box(
           modifier = Modifier
               .fillMaxWidth()
               .border(1.dp, color = Color(0xFF9C89B8), shape = RoundedCornerShape(10.dp))
               .clickable { navController.navigate(Screen.AddTask.name) }
               .padding(15.dp)
       ) {
           Text(text = "Add Task", modifier = Modifier.align(Alignment.Center))
       }

       Spacer(modifier = Modifier.height(10.dp))

       Box(
           modifier = Modifier
               .fillMaxWidth()
               .border(1.dp, color = Color(0xFF9C89B8), shape = RoundedCornerShape(10.dp))
               .clickable { navController.navigate(Screen.AddRoutine.name) }
               .padding(15.dp)
       ) {
           Text(text = "Add Routine", modifier = Modifier.align(Alignment.Center))
       }
   }
}