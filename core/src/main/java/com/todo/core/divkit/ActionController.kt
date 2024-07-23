package com.todo.core.divkit

import androidx.navigation.NavController

class ActionController(
    private val navController: NavController,
) {

    fun action(action: DivKitActions) {
        when (action) {
            DivKitActions.NavigateUp -> navController.navigateUp()
            //.. You can implement more ..
        }
    }
}

enum class DivKitActions {
    NavigateUp
}