package com.example.phinconattendance.data.database

import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    //insert user to database after register
    fun insertUser(key: String, item: UserResponse.User): Flow<ResultState<String>>

    //get user
    fun getUser(userId: String) : Flow<ResultState<UserResponse>>

    //insert attendance to specific id after check in or check out
    fun insertAttendance(userId:String,key: String, item: AttendanceResponse.Attendance): Flow<ResultState<String>>

    //get attendance item by userId
    fun getAttendance(userId: String) : Flow<ResultState<List<AttendanceResponse>>>
}