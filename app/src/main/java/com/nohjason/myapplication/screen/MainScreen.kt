package com.nohjason.myapplication.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nohjason.myapplication.Screen
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.network.Routine
import com.nohjason.myapplication.network.Task
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val routin by viewModel.routines
    val tasks by viewModel.tasksByDate
    var selectedDate by remember { mutableStateOf(LocalDate.now().toString()) }
    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = dateFormat.format(calendar.time)
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(Unit) {
        viewModel.fetchRoutine()
        viewModel.fetchTasksDate(selectedDate)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To Do List") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("selectScreen")
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "This is Add Fab",
                            tint = Color.Black,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            viewModel.fetchTasksDate(selectedDate)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF9C89B8))
                        .clickable { datePickerDialog.show() }
                        .padding(10.dp)
                ) {
                    Text(text = "Select Date", color = Color.White)
                }
                Spacer(modifier = Modifier.weight(0.1f))
                Text(text = selectedDate, fontSize = 20.sp)
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Text(text = "Routine", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    LazyColumn(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    ) {
                        items(routin) { routine ->
                            var check by remember { mutableStateOf(if (routine.state == "active") false else true) }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            routine.id?.let {
                                                viewModel.updateSetRoutine(
                                                    it,
                                                    Routine(
                                                        routine.name,
                                                        routine.importanceEnum,
                                                        routine.colorEnum
                                                    )
                                                )
                                                check = !check
                                            }
                                        }
                                        .background(
                                            color =
                                            if (check) getColorFromEnum(routine.colorEnum) else Color.White
                                        )
                                        .border(
                                            3.dp,
                                            color = if (check) Color.Transparent else getColorFromEnum(
                                                routine.colorEnum
                                            ),
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

                                CardRoutine(
                                    routine,
                                    check,
                                    deleteOnClick = {
                                        viewModel.deleteRoutine(routine.id!!)
                                        viewModel.fetchRoutine()
                                    },
                                    updateOnClick = {
                                        navController.navigate(Screen.RoutineUpdate.name + "/${routine.id!!}/${routine.name}")
                                    },
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    Text(text = "Task", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    LazyColumn(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(vertical = 10.dp, horizontal = 20.dp)
                    ) {
                        items(tasks) { tasks ->
                            var check by remember { mutableStateOf(if (tasks.status == "active") false else true) }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .clickable {
                                            tasks.id?.let {
                                                viewModel.updateSetTask(
                                                    it,
                                                    tasks.date
                                                )
                                                check = !check
                                            }
                                        }
                                        .background(
                                            color =
                                            if (check) getColorFromEnum(tasks.colorEnum) else Color.White
                                        )
                                        .border(
                                            3.dp,
                                            color = if (check) Color.Transparent else getColorFromEnum(
                                                tasks.colorEnum
                                            ),
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

                                CardTask(
                                    tasks,
                                    check,
                                    deleteOnClick = {
                                        viewModel.deleteTask(tasks.id!!, selectedDate)
                                    },
                                    updateOnClick = {
                                        navController.navigate(Screen.TaskUpdate.name + "/${tasks.id!!}/${tasks.name}")
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardRoutine(
    routine: Routine,
    check: Boolean,
    deleteOnClick: () -> Unit,
    updateOnClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = getColorFromEnum(routine.colorEnum)
            )
            .padding(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = routine.name,
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
fun CardTask(
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