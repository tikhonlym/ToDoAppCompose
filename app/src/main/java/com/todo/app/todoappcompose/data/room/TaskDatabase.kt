package com.todo.app.todoappcompose.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todo.app.todoappcompose.domain.model.Task

@Database(
    entities = [
        Task::class
    ],
    version = 1
)

/** Responsible for providing an API for working with local data */

abstract class TaskDatabase : RoomDatabase() {
    abstract val dao: TaskDao
}


