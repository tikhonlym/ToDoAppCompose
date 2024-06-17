package com.todo.app.todoappcompose.presentation.edit

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable


@Serializable
data class EditToDoScreenDestination(
    val id: String
)

@Composable
fun EditToDoScreen(
    id: String,
    navController: NavController,
) {
    //..
}