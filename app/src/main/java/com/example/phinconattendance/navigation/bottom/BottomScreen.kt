package com.example.phinconattendance.navigation.bottom

import com.example.phinconattendance.R

sealed class BottomScreen(var title: String, var icon: Int, var screen_route:String){
    object Home : BottomScreen("Home", R.drawable.ic_home,"home")
    object History : BottomScreen("History",R.drawable.ic_history,"history")
    object Profile : BottomScreen("Profile",R.drawable.ic_profile,"profile")
}