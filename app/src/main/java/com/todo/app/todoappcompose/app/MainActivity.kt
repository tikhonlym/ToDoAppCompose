package com.todo.app.todoappcompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.presentation.MainScreenApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreenApp()
            }
        }
    }
}