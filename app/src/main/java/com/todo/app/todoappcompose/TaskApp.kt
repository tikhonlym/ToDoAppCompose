package com.todo.app.todoappcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //..
    }
}