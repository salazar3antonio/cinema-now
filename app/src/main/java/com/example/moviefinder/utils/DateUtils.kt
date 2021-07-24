package com.example.moviefinder.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

fun formatLongDate(releaseDate: String): String {

    val input = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val output = SimpleDateFormat("MMMM d, yyyy", Locale.US)
    val date = input.parse(releaseDate)
    return output.format(date)

}