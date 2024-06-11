package com.example.phinconattendance.data.model

data class AttendanceResponse(
    val item : Attendance?,
    val key : String? =""
) {
    data class Attendance(
        val company : String?="",
        val address: String="",
        val time: Long? = 0,
        val attend: String? ="",
    )
}

