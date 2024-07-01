package com.todo.app.todoappcompose.data.source.local

import com.todo.app.todoappcompose.domain.objects.TodoImportance
import com.todo.app.todoappcompose.domain.objects.TodoItem
import java.time.LocalDate

object TestData {
    // Test Data
    val dataList = mutableListOf<TodoItem>(
        TodoItem(
            "1",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "2",
            "Купить что-то,",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
            deadline = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "3",
            "Купить что-то, где- поня как обр…",
            TodoImportance.LOW,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "4",
            "Купить что-то, где-тоо точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
            deadline = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "5",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "6",
            "Купить что-то, где-то, онятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "7",
            "Купить что-то..",
            TodoImportance.LOW,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "8",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
            deadline = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "9",
            "Купить что-то,",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "10",
            "Купить что-то, где- поня как обр…",
            TodoImportance.LOW,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "11",
            "Купить что-то, где-тоо точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
            deadline = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "12",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "13",
            "Купить что-то, где-то, онятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "14",
            "Купить что-то..",
            TodoImportance.LOW,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30),
            deadline = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "15",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "16",
            "Купить что-то,",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "17",
            "Купить что-то, где- поня как обр…",
            TodoImportance.LOW,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30),
            deadline = LocalDate.of(2024, 6, 30)
        ),
        TodoItem(
            "18",
            "Купить что-то, где-тоо точно чтобы показать как обр…",
            TodoImportance.NORMAL,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "19",
            "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "20",
            "Купить что-то, где-то, онятно, но точно чтобы показать как обр…",
            TodoImportance.HIGH,
            isDone = false,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
        TodoItem(
            "21",
            "Купить что-то..",
            TodoImportance.LOW,
            isDone = true,
            creationDate = LocalDate.of(2024, 6, 30),
        ),
    )
}