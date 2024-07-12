package com.todo.domain.usecase

import com.todo.domain.TaskDao
import com.todo.domain.model.Task
import javax.inject.Inject

class GetLocalListUseCase @Inject constructor(
    private val local: TaskDao,
) {

    suspend fun execute(): List<Task> {
        return local.getTaskList()
    }
}