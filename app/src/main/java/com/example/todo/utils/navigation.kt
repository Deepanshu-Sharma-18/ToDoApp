package com.example.todo.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.views.AddTask
import com.example.todo.views.AddTodo
import com.example.todo.views.EditScreen
import com.example.todo.views.ToDoScreen

@Composable
fun Navigation_Todo() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MAINSCREEN.name){
        composable(Routes.MAINSCREEN.name){
            ToDoScreen(navController)
        }
        composable(Routes.ADDTODO.name){
          AddTodo(navController = navController)
        }
        composable(Routes.DETAILSCREEN.name+"/{id}", arguments = listOf(navArgument(name = "id" ){
            type = NavType.IntType
        })){
            AddTask(it.arguments!!.getInt("id"),navController)
        }
        composable(Routes.EDITSCREEN.name+"/{id}",arguments = listOf(navArgument(name = "id"){
            type = NavType.IntType
        })){
            EditScreen(it.arguments!!.getInt("id"),navController)
        }
    }
}