package com.todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey
    val id: String,
    val text: String,
    val importance: TaskImportance = TaskImportance.basic,
    val deadline: Long? = null,
    val done: Boolean = false,
    val created_at: Long,
    val changed_at: Long,
    val last_updated_by: String,
)
