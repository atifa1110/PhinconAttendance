package com.example.phinconattendance.data.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Time {
    var now = System.currentTimeMillis()

    fun convertToHour(time: Long?=0): Long {
        val diff = now - time!!
        return TimeUnit.MILLISECONDS.toHours(diff)
    }

    fun convertToDay(time: Long?=0): Long {
        val diff = now - time!!
        return TimeUnit.MILLISECONDS.toDays(diff)
    }

    fun convertToTime(time : Long?=0) :String{
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(time!!)
    }
}