package com.todo.app.todoappcompose.presentation.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

fun generateUniqueIdForTask(): String {
    return UUID.randomUUID().toString()
}

fun millisecondsToLocalDate(milliseconds: Long): LocalDate {
    val instant = Instant.ofEpochMilli(milliseconds)
    val zoneId = ZoneId.systemDefault()
    return instant.atZone(zoneId).toLocalDate()
}