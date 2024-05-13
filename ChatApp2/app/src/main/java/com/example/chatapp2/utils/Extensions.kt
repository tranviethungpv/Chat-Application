package com.example.chatapp2.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTimeDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm dd/MM", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}