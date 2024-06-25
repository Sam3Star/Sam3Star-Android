package com.nohjason.myapplication.screen

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nohjason.myapplication.Screen
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.network.Task
import com.nohjason.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    LaunchedEffect(Unit) { viewModel.fetchTasks() }
    val tasks by viewModel.tasks

    Column {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                items(tasks) { task ->
                    var check by remember { mutableStateOf(if (task.state == "active") false else true) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    task.id?.let {
                                        viewModel.updateRoutine(
                                            it,
                                            Task(task.name, task.importanceEnum, task.colorEnum)
                                        )
                                        check = !check
                                    }
                                }
                                .background(
                                    color =
                                    if (check) getColorFromEnum(task.colorEnum) else Color.White
                                )
                                .border(
                                    3.dp,
                                    color = if (check) Color.Transparent else getColorFromEnum(task.colorEnum),
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "check",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Card(
                            task,
                            check,
                            deleteOnClick = {
                                viewModel.deleteTask(task.id!!)
                                viewModel.fetchTasks()
                            },
                            updateOnClick = {
                                navController.navigate(Screen.Update.name + "/${task.id!!}/${task.name}")
                            },
                        )
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Add.name)
                },
                containerColor = Color.Gray,
                shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp)),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
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
    check: Boolean,
    deleteOnClick: () -> Unit,
    updateOnClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = getColorFromEnum(task.colorEnum)
            )
            .padding(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.name,
                textDecoration = if (check) TextDecoration.LineThrough else null,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "update",
                modifier = Modifier.clickable { updateOnClick() }
            )
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "delete",
                modifier = Modifier.clickable { deleteOnClick() }
            )
        }
    }
}

@Composable
fun getColorFromEnum(color: String): Color {
    return when (color) {
        "green" -> Color(0xFFCEEDC7)
        "red" -> Color(0xFFFF9494)
        "orange" -> Color(0xFFFFD4B2)
        "yellow" -> Color(0xFFFFF6BD)
        "blue" -> Color(0xFFD7E3FC)
        else -> Color(0xFFFFC8DD)
    }
}


@Preview(showBackground = true)
@Composable
fun WhatText() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Blue)
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "check",
            modifier = Modifier.size(30.dp)
        )
    }
}