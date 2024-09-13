package com.example.phinconattendance.data.repository

import com.example.phinconattendance.data.model.ResultState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    val currentUser: FirebaseUser?
    fun resetPassword(email:String): Flow<ResultState<String>>
    fun updatePassword(password:String) : Flow<ResultState<String>>
    fun loginUser(email: String, password: String): Flow<ResultState<String>>
    fun registerUser(name: String, email: String, password: String): Flow<ResultState<String>>
    fun logout()
}