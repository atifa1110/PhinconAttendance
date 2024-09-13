package com.example.phinconattendance.data.mapper

import com.example.phinconattendance.data.model.Attendance
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.Place
import com.example.phinconattendance.data.model.PlaceResponse

internal fun Place.asAttendance(time : Long, attend:String) = Attendance(
    company = company,
    address = address,
    time = time,
    attend = attend
)