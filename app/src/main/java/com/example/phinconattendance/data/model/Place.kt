package com.example.phinconattendance.data.model

//data class Place(
//    val id: Int?,
//    val company: String,
//    val address: String,
//    val photo : Int
//)

data class PlaceResponse(
    val item : Place?,
    val id : Int = 0
) {
    data class Place(
        val company: String = "",
        val address: String = ""
    )
}