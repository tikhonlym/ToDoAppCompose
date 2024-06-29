package com.todo.app.todoappcompose.data.source.local

import com.todo.app.todoappcompose.domain.objects.TaskDate
import com.todo.app.todoappcompose.domain.objects.TodoImportance
import com.todo.app.todoappcompose.domain.objects.TodoItem

object TestData {
    // Test Data
    val dataList = mutableListOf<TodoItem>(
        TodoItem(
            "1",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "2",
            "Купить что-то,",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
            deadline = TaskDate(0L)
        ),
        TodoItem(
            "3",
            "Купить что-то, где- поня как обр…",
            TodoImportance.LOW,
            isDone = true,
            creationDate = TaskDate(10203L)
        ),
        TodoItem(
            "4",
            "Купить что-то, где-тоо точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
            deadline = TaskDate(0L)
        ),
        TodoItem(
            "5",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = true,
            creationDate = TaskDate(10203L)
        ),
        TodoItem(
            "6",
            "Купить что-то, где-то, онятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = false,
            creationDate = TaskDate(10203L)
        ),
        TodoItem(
            "7",
            "Купить что-то..",
            TodoImportance.LOW,
            isDone = true,
            creationDate = TaskDate(10203L)
        ),
        TodoItem(
            "8",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
            deadline = TaskDate(0L)
        ),
        TodoItem(
            "9",
            "Купить что-то,",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "10",
            "Купить что-то, где- поня как обр…",
            TodoImportance.LOW,
            isDone = true,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "11",
            "Купить что-то, где-тоо точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
            deadline = TaskDate(0L)
        ),
        TodoItem(
            "12",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = true,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "13",
            "Купить что-то, где-то, онятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "14",
            "Купить что-то..",
            TodoImportance.LOW,
            isDone = true,
            creationDate = TaskDate(10203L),
            deadline = TaskDate(0L)
        ),
        TodoItem(
            "15",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "16",
            "Купить что-то,",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "17",
            "Купить что-то, где- поня как обр…",
            TodoImportance.LOW,
            isDone = true,
            creationDate = TaskDate(10203L),
            deadline = TaskDate(0L)
        ),
        TodoItem(
            "18",
            "Купить что-то, где-тоо точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "19",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = true,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "20",
            "Купить что-то, где-то, онятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = false,
            creationDate = TaskDate(10203L),
        ),
        TodoItem(
            "21",
            "Купить что-то..",
            TodoImportance.LOW,
            isDone = true,
            creationDate = TaskDate(10203L),
        ),
    )
}