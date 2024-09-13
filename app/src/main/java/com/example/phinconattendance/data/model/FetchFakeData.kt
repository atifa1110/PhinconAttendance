package com.example.phinconattendance.data.model

fun fetchAttendanceList(): List<Attendance> {
    return listOf(
        Attendance(
            "PT.Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1690224493994,
            "Check in"
        ),
        Attendance(
            "PT.Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1690224493994,
            "Check in"
        ),
        Attendance(
            "PT.Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1690224493994,
            "Check in"
        ),
        Attendance(
            "PT.Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1690224493994,
            "Check in"
        ),
    )
}

fun fetchPlaceList(): List<Place> {
    return listOf(
        Place(
            "PT. Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
        ),
        Place(
            "Telkomsel Smart Office",
            "Jl. Jend. Gatot Subroto Kav. 52. Jakarta Selatan",
        ),
        Place(
            "Rumah",
            "Jakarta"
        ),
    )
}

fun fetchAttendanceResponse() = AttendanceResponse(
    item =
    Attendance(
        "PT. Phincon",
        "Office. 88 @Kasablanka Office Tower 18th Floor",
        1725951320661,
        "Check out"
    ), key = "12345")

fun fetchAttendanceResponseList() = listOf(
    AttendanceResponse(
        item = Attendance(
            "PT. Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1725951320661,
            "Check out"),
        key = "12345"),
    AttendanceResponse(
        item = Attendance(
            "PT. Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1725951320661,
            "Check out"),
        key = "23467"),
    AttendanceResponse(
        item = Attendance(
            "PT. Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            1725951320661,
            "Check out"),
        key = "45678")

)