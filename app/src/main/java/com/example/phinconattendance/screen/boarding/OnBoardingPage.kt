package com.example.phinconattendance.screen.boarding

import androidx.annotation.DrawableRes
import com.example.phinconattendance.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.first,
        title = "DIGITAL ABSENSI",
        description = "Kehadiran sisten absensi digital merupakan " +
                "\npenemuan yang mampu menggantikan pencatatan" +
                "\n data kehadiran secara manual"
    )

    object Second : OnBoardingPage(
        image = R.drawable.second,
        title = "ATTENDANCE SYSTEM",
        description = "Pengelolaan karyawan di era digital yang baik," +
                "\nmenghasilkan karyawan terbaik pula, salah satunya" +
                "\nabsensi karyawan"
    )

    object Third : OnBoardingPage(
        image = R.drawable.third,
        title = "SELALU PAKAI MASKER",
        description = "Guna mencegah penyebaran virus Covid-19," +
                "\nPemerintah telah mengeluarkan kebijakan Physical" +
                "\nDistancing serta kebijakan bekerja, belajar, dan" +
                "\nberibadah dari rumah."
    )
}
