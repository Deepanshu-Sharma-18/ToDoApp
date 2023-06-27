package com.example.todo.views.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todo.model.TodoModel
import com.example.todo.provider.Viewmodel
import com.example.todo.utils.Routes
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun TodoCard(todoModel: TodoModel,navController: NavController, onClicked :() -> Unit,todoViewModel: Viewmodel = hiltViewModel()) {

   Surface(
      modifier = Modifier
         .fillMaxWidth()
         .height(160.dp)
         .clip(shape = RoundedCornerShape(corner = CornerSize(35.dp)))
         .clickable {
            navController.navigate(Routes.DETAILSCREEN.name + "/${todoModel.id}")
         }
   ) {
      Column(
         modifier = Modifier
            .background(color = if (!todoModel.done) MaterialTheme.colorScheme.primary else Color(
               0xB4505C7A
            ), shape = RoundedCornerShape(corner = CornerSize(35.dp)))
            .padding(15.dp),
         verticalArrangement = Arrangement.Top,
         horizontalAlignment = Alignment.Start
      ) {
         Row (modifier = Modifier
            .width(150.dp)
            .align(Alignment.End), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Text(
               modifier = Modifier,
               text = (todoModel.date.hour.toString() + ":" + todoModel.date.minute.toString()),
               fontSize = 15.sp,
               fontWeight = FontWeight.ExtraBold,
               color = Color.Black,
               textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(5.dp))
            Card(
               modifier = Modifier
                  .size(40.dp)
                  .clip(shape = CircleShape)
                  .clickable {
                     GlobalScope.launch {
                        todoViewModel.updateNote(TodoModel(todoModel.id,!todoModel.done,todoModel.title,todoModel.description,todoModel.date,todoModel.created))
                     }

                  }
            ) {
               Column(
                  modifier = Modifier
                     .fillMaxSize()
                     ,
                  verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally
               ) {
                  Icon(
                     imageVector =
                     if(!todoModel.done)
                        Icons.Rounded.CheckCircle
                     else
                        Icons.Rounded.Clear,
                     contentDescription = "add",
                     tint = Color(0xFFFDFDFD),
                     modifier = Modifier.size(30.dp)
                  )
               }
            }
            Card(
               modifier = Modifier
                  .size(40.dp)
                  .clip(shape = CircleShape)
                  .clickable {
                     onClicked()
                  }
            ) {
               Column(
                  modifier = Modifier
                     .fillMaxSize()
                  ,
                  verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally
               ) {
                  Icon(
                     imageVector =

                        Icons.Rounded.Delete
                    ,
                     contentDescription = "add",
                     tint = Color(0xFFFDFDFD),
                     modifier = Modifier.size(30.dp)
                  )
               }
            }
         }
         Text(
            text = todoModel.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 35.sp,
            fontWeight = FontWeight.W500,
            color = Color.Black
         )

         Text(
            modifier=Modifier.fillMaxWidth(),
            text = todoModel.description,
            fontSize = 20.sp,
            fontWeight = FontWeight.W300,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis
         )
      }

   }
}