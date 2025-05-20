package com.example.newsaggregator.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.toDate(): Date {
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    return formatter.parse(this)
}

fun Long.toDateUi(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("HH:mm d MMMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

