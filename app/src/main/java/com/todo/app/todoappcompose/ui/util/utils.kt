package com.todo.app.todoappcompose.ui.util

import android.os.Build
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

fun generateUniqueIdForTask(): String {
    return UUID.randomUUID().toString()
}

fun LocalDate.formatToString(pattern: String = "dd MMMM yyyy"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return this.format(formatter)
}

fun formatToMillis(localDate: LocalDate): Long {
    val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return instant.toEpochMilli()
}

fun millisecondsToLocalDate(milliseconds: Long): LocalDate {
    val instant = Instant.ofEpochMilli(milliseconds)
    val zoneId = ZoneId.systemDefault()
    return instant.atZone(zoneId).toLocalDate()
}

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.lowercase(Locale.getDefault())
            .startsWith(manufacturer.lowercase(Locale.getDefault()))
    ) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}

private fun capitalize(text: String?): String {
    if (text.isNullOrEmpty()) {
        return ""
    }
    val first = text[0]
    return if (Character.isUpperCase(first)) {
        text
    } else {
        first.uppercaseChar().toString() + text.substring(1)
    }
}