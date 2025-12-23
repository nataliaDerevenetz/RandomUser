package com.example.randomuserapp.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun String.toDateFormatter(): Date {
    val pattern = "dd/MM/yyyy"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDate = LocalDate.parse(this, formatter)
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun String.toDateFromServerFormatted(): Date {
    val datePart = this.substringBefore("T")
    val localDate = LocalDate.parse(datePart)
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.toFormattedString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(this)
}