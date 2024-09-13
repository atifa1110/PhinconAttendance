package com.example.phinconattendance.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse(
    val item : Place?,
    val id : Int = 0
)

@Serializable
data class Place(
    val company: String = "",
    val address: String = ""
)