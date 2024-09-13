package com.example.phinconattendance.data.repository

import com.example.phinconattendance.data.model.ResultState
import com.example.phinconattendance.data.model.Attendance
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.User
import com.example.phinconattendance.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    //insert user to database after register
    fun insertUser(key: String, item: User): Flow<ResultState<String>>

    //get user
    fun getUser(userId: String) : Flow<ResultState<UserResponse>>

    //insert attendance to specific id after check in or check out
    fun insertAttendance(userId:String,key: String, item: Attendance): Flow<ResultState<String>>

    //get attendance item by userId
    fun getAttendance(userId: String) : Flow<ResultState<List<AttendanceResponse>>>
}