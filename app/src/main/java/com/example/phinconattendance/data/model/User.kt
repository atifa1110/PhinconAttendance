package com.example.phinconattendance.data.model

data class UserResponse(
    val item : User?,
    val key : String? = ""
){
    data class User(
        val name:String? = "",
        val email:String? = "",
        val photo:String?="",
        val address: String?="",
        val employee : Long? =0
    )
}