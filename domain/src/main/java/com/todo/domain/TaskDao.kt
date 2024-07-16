package com.todo.domain

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.todo.domain.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTask(id: String): Task

    @Query("SELECT * FROM task")
    suspend fun getTaskList(): List<Task>

    @Upsert
    suspend fun upsertTask(task: Task)

    @Upsert
    suspend fun upsertTasks(tasks: List<Task>)

    @Query("DELETE FROM task")
    suspend fun clearTable()

    @Transaction
    suspend fun replaceTable(tasks: List<Task>) {
        clearTable()
        tasks.forEach { upsertTask(it) }
        upsertTasks(tasks)
    }

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun deleteTaskById(id: String)

}
