package com.nohjason.myapplication.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nohjason.myapplication.Screen
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.network.Task
import com.nohjason.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    LaunchedEffect(Unit) { viewModel.fetchTasks() }
    val tasks by viewModel.tasks


    Column {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(tasks) { task ->
                    Card(task, onClick = {viewModel.deleteTask(task.id?:0)})
                    Divider()
                }
            }
            FloatingActionButton(
                onClick = {
                    // 클릭 시 실행할 코드
                    navController.navigate(Screen.Add.name)
                },
                containerColor = Color.Gray,
                shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp)),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp), // 오른쪽 하단에서 16dp 패딩
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "This is Add Fab",
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun Card(
    task: Task,
    onClick: () ->Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(10.dp)
            .clickable {
                onClick()
            }
    ) {
        var check by remember {
            mutableStateOf(false)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = check,
                onCheckedChange = { check = it }
            )
            Text(text = task.name)
            Spacer(modifier = Modifier.weight(0.1f))
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "update"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
//        Card(title = "wow")
    }
}