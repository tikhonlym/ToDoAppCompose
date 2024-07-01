package com.todo.app.todoappcompose.presentation.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

fun generateUniqueIdForTask(): String {
    return UUID.randomUUID().toString()
}

fun LocalDate.formatToString(pattern: String = Constants.DEFAULT_DATE_PATTERN): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

fun millisecondsToLocalDate(milliseconds: Long): LocalDate {
    val instant = Instant.ofEpochMilli(milliseconds)
    val zoneId = ZoneId.systemDefault()
    return instant.atZone(zoneId).toLocalDate()
}