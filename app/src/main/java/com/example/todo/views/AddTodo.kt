package com.example.todo.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog

import android.widget.DatePicker
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todo.model.TodoModel
import com.example.todo.provider.Viewmodel

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    DelicateCoroutinesApi::class
)
@Composable
fun AddTodo(navController : NavController, todoViewmodel : Viewmodel = hiltViewModel()) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val year = remember {
        mutableStateOf(0)
    }
    val month = remember {
        mutableStateOf(0)
    }
    val day = remember {
        mutableStateOf(0)
    }
    val min = remember {
        mutableStateOf(0)
    }
    val hour = remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Column(modifier = Modifier

        .fillMaxSize()
        .verticalScroll(scrollState)
        .clickable {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
        .background(color = Color(0xFFC0A657))
        .padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start){
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
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = "add",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Title",fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = title.value, onValueChange = {
            title.value = it
        }, textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFC0A657)
            ))

        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Description",fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = description.value, onValueChange = {
            description.value = it
        }, textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFC0A657)
            ))

        Spacer(modifier = Modifier.height(80.dp))

        val mYear: Int
        val mMonth: Int
        val mDay: Int

        val mCalendar = Calendar.getInstance()

        val mHour = mCalendar[Calendar.HOUR_OF_DAY]
        val mMinute = mCalendar[Calendar.MINUTE]
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()



        val mDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
               day.value = mDayOfMonth
                year.value = mYear
                month.value = mMonth
            }, mYear, mMonth, mDay
        )
        val mTimePickerDialog = TimePickerDialog(
            context,
            {_, mHour : Int, mMinute: Int ->
              hour.value = mHour
                min.value = mMinute
            }, mHour, mMinute, false
        )


        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                        .background(
                            color = Color(0xFF243D69),
                            shape = RoundedCornerShape(corner = CornerSize(15.dp))
                        )
                        .clickable {
                            mDatePickerDialog.show()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pick Date" ,fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }


            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                        .background(
                            color = Color(0xFF243D69),
                            shape = RoundedCornerShape(corner = CornerSize(15.dp))
                        )
                        .clickable {
                            mTimePickerDialog.show()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pick Time" ,fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(30.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Selected Date",fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(text = "${day.value}/${month.value+1}/${year.value}", fontSize = 20.sp, fontWeight = FontWeight.W300, textAlign = TextAlign.Center)

            }
            Column(modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Selected Time",fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(text = " ${hour.value}:${min.value}", fontSize = 20.sp, fontWeight = FontWeight.W300, textAlign = TextAlign.Center)
            }
        }



        Spacer(modifier = Modifier.height(80.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()

                    .background(
                        color = Color(0xFF243D69),
                        shape = RoundedCornerShape(corner = CornerSize(15.dp))
                    )
                    .clickable {
                        GlobalScope.launch {
                            todoViewmodel.addNote(
                                TodoModel(
                                    title = title.value,
                                    description = description.value,
                                    date = LocalDateTime.of(
                                        year.value,
                                        month.value+1,
                                        day.value,
                                        hour.value,
                                        min.value,
                                        0
                                    ),
                                    created = LocalDateTime.now()

                                )
                            )
                        }
                        navController.popBackStack()
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Save" ,fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

    }
}