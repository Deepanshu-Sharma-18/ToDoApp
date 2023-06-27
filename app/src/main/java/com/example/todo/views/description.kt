package com.example.todo.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todo.model.TodoModel
import com.example.todo.provider.Viewmodel
import com.example.todo.utils.Routes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(id: Int, navController: NavHostController, todoViewModel : Viewmodel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val datetime = LocalDateTime.now()
    val execute = remember{
        mutableStateOf(false)
    }
    val todoModel = remember {
        mutableStateOf<TodoModel?>(null)
    }

    for (i in 0 until todoViewModel.notelist.value.size){
        if (id == todoViewModel.notelist.value[i].id){
            todoModel.value = todoViewModel.notelist.value[i]
            Log.d("com.example.todo" , todoModel.toString())
            execute.value= true
        }
    }
    if (execute.value)
    Column(modifier = Modifier

        .fillMaxSize()
        .verticalScroll(scrollState)
        .background(color = Color(0xFFC0A657))
        .padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {

        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Card(
                modifier = Modifier
                    .size(55.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        navController.popBackStack()
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFF243D69)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "add",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .size(55.dp)
                    .clip(shape = CircleShape)
                    .clickable {
                        navController.navigate(Routes.EDITSCREEN.name + "/${todoModel.value!!.id}")
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFF243D69)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "add",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))

            Text(todoModel.value!!.title,
                fontSize = 80.sp,
                lineHeight = 80.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )



        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Time Left",fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Text(
            text =  Math.abs(todoModel.value!!.date.hour-datetime.hour).toString()+"h" + " "+ Math.abs(todoModel.value!!.date.minute-datetime.minute).toString() +"m"  ,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
        Text(
            text =  todoModel.value!!.date.month.toString() + " " + todoModel.value!!.date.dayOfMonth.toString() + "," + todoModel.value!!.date.year.toString() ,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Description",fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = todoModel.value!!.description,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Created",fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = todoModel.value!!.created.month.toString() + " " + todoModel.value!!.created.dayOfMonth.toString() + "," + todoModel.value!!.created.year.toString() ,fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(60.dp))

    }

}