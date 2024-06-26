package com.nohjason.myapplication.screen.add

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nohjason.myapplication.network.MainViewModel
import com.nohjason.myapplication.network.Routine
import kotlin.collections.listOf

@Composable
fun RoutineAddScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
//    var startDate by remember { mutableStateOf("") }
//    var endDate by remember { mutableStateOf("") }
    var importance by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 60.dp),
    ) {
        Text(
            text = "Routine",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(),
        )

        Text(text = "Name")
        TextField(value = text, onValueChange = {text = it})

        Spacer(modifier = Modifier.height(10.dp))

//        Text(text = "Start Date")
//        startDate = Date()
//
//        Text(text = "End Date")
//        endDate = Date()

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Level Importance")
        importance = DropdownButton()

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Color")
        var selectedBoxIndex by remember { mutableStateOf(-1) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ColorBox(color = Color(0xFFCEEDC7), isSelected = selectedBoxIndex == 0) { selectedBoxIndex = 0 }
            ColorBox(color = Color(0xFFFF9494), isSelected = selectedBoxIndex == 1) { selectedBoxIndex = 1 }
            ColorBox(color = Color(0xFFFFD4B2), isSelected = selectedBoxIndex == 2) { selectedBoxIndex = 2 }
            ColorBox(color = Color(0xFFFFF6BD), isSelected = selectedBoxIndex == 3) { selectedBoxIndex = 3 }
            ColorBox(color = Color(0xFFD7E3FC), isSelected = selectedBoxIndex == 4) { selectedBoxIndex = 4 }
            ColorBox(color = Color(0xFFFFC8DD), isSelected = selectedBoxIndex == 5) { selectedBoxIndex = 5 }
        }

        val colorList = listOf(
            "green",
            "red",
            "orange",
            "yellow",
            "blue",
            "pink"
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF9C89B8))
                .clickable {
                    if (text.isNotEmpty() && importance.isNotEmpty()  && selectedBoxIndex != -1) {
                        val routine = Routine(
                            name = text,
                            importanceEnum = importance,
                            colorEnum = colorList[selectedBoxIndex]
//                            startAt = startDate,
//                            endAt = endDate
                        )
                        Log.d("TAG", "AddScreen: $routine")
                        viewModel.createRoutine(routine)
                        navController.popBackStack()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "모든 항목을 채워 주세요", Toast.LENGTH_SHORT).show()
                    }
                }
        ) {
            Text(
                text = "Add",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ColorBox(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color.Black else Color.Transparent
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .clickable { onClick() }
            .border(2.dp, borderColor, shape = RoundedCornerShape(8.dp))
    )
}

@Composable
fun DropdownButton(): String {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Level") }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable { expanded = !expanded }
                .border(1.dp, Color.Black, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = text)
                Spacer(modifier = Modifier.weight(0.1f))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }
        CustomDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("veryImportant") },
                onClick = {
                    text = "veryImportant"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("important") },
                onClick = {
                    text = "important"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("soso") },
                onClick = {
                    text = "soso"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("baggy") },
                onClick = {
                    text = "baggy"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("less") },
                onClick = {
                    text = "less"
                    expanded = false
                }
            )
        }
    }
    return text
}

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    menuItems: @Composable () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        menuItems()
    }
}