package com.example.todo.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.todo.model.TodoModel
import com.example.todo.provider.Viewmodel
import com.example.todo.utils.Routes
import com.example.todo.views.components.TodoCard
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import java.time.YearMonth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(navController: NavHostController, ) {



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary
    ) { contentPadding ->
        mainView(contentPadding = contentPadding,navController=navController)
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun mainView(
    contentPadding: PaddingValues,
    todoViewModel : Viewmodel = hiltViewModel(),
    navController: NavHostController,
    ) {
    val datetime = LocalDateTime.now()
    val todoList by todoViewModel.notelist.collectAsState()
    val active = remember {
        mutableStateOf(datetime.dayOfMonth)
    }

    val datavisible = remember {
        mutableStateListOf<TodoModel>()
    }
    val tasksCompleted = remember {
        mutableStateOf(0)
    }

    val configuration = LocalConfiguration.current

    val widthScreen = configuration.screenWidthDp


    val scrollstate = rememberScrollState()
    val rowScrollState = rememberScrollState(
        ((active.value-2)*198)
    )


    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = datetime.format(format)
    val noOfDaysMonth = YearMonth.of(datetime.year,datetime.monthValue)
    val noOfDays = noOfDaysMonth.lengthOfMonth()

    LaunchedEffect(key1 = todoList){

        datavisible.clear()
        todoList
            .filter {
                it.date.dayOfMonth == active.value
            }
            .map { datavisible.add(it) }
        var doneCount =0
        var totalCount =0
        for(item in datavisible){

            if (item.done == true){
                doneCount++
            }
            totalCount++;
        }
        if (totalCount ==0){
            tasksCompleted.value = 100
        }else{
            tasksCompleted.value = (doneCount * 100 / totalCount).toInt()
        }
    }


    Column(
        modifier = Modifier
            .verticalScroll(state = scrollstate)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                modifier = Modifier
                    .size(55.dp)

                    .clip(shape = CircleShape)
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1780&q=80",
                    contentDescription = "image_avatar"
                )
            }
            Spacer(modifier = Modifier.width(200.dp))

            Row(
                modifier = Modifier.width(120.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(shape = CircleShape)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF243D69)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Notifications,
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
                            navController.navigate(
                                Routes.ADDTODO
                                    .name
                            )
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
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "add",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = if (datetime.hour < 12) "Good \n\n\nMorning" else "Good \n\n\nEvening",
            fontSize = 80.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Today's",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = date.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${tasksCompleted.value}% done",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Completed Tasks",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Tasks",
            fontSize = 40.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "${datetime.month}",
            fontSize = 30.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rowScrollState),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Log.d("ROWSCROLLSTATE",rowScrollState.value.toString())
            for (i in 1..noOfDays){
                Card(modifier = Modifier
                    .height(90.dp)
                    .width(75.dp)
                    .padding(7.dp,0.dp)
                    .clickable {
                        active.value = i
                        datavisible.clear()
                        todoList
                            .filter {
                                it.date.dayOfMonth == active.value
                            }
                            .map { datavisible.add(it) }

                        var doneCount = 0
                        var totalCount = 0
                        for (item in datavisible) {

                            if (item.done == true) {
                                doneCount++
                            }
                            totalCount++;
                        }
                        if (totalCount == 0) {
                            tasksCompleted.value = 100
                        } else {

                            tasksCompleted.value = (doneCount * 100 / totalCount).toInt()
                        }
                    }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = if (active.value == i) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = RoundedCornerShape(corner = CornerSize(5.dp))
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "${i}",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.W400
                    )
                    val date = LocalDate.of(datetime.year, datetime.month, i)
                    Text(
                        text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W400
                    )
                }
            }
            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        if (datavisible.size == 0){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text(
                    text = "No Tasks Available",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
        else{

            for ( item in datavisible){
                Log.d("Weekday", ".")
                TodoCard(
                    item,
                    onClicked = {
                        GlobalScope.launch {
                            todoViewModel.removeNote(item)
                        }
                    },
                    navController = navController
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

