package com.example.phinconattendance.data.model

import com.example.phinconattendance.R

fun fetchPlaceList(): List<PlaceResponse.Place> {
    return listOf(
        PlaceResponse.Place(
            "PT. Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
        ),
        PlaceResponse.Place(
            "Telkomsel Smart Office",
            "Jl. Jend. Gatot Subroto Kav. 52. Jakarta Selatan",
        ),
        PlaceResponse.Place(
            "Rumah",
            "Jakarta"
        ),
    )
}