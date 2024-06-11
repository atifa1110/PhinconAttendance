package com.example.phinconattendance.navigation

sealed class Screen(val route: String){
    //Graph Screen
    object Root : Screen("root_screen")
    object Authentication : Screen ("authentication_screen")
    object Main : Screen("main_screen")

    //authentication
    object Splash : Screen("splash_screen")
    object OnBoarding : Screen ("boarding_screen")
    object Login : Screen ("login_screen")
    object Register : Screen ("register_screen")
    object Forgot : Screen("forgot_password_screen")

    //main
    object Home : Screen("home_screen")
    object Profile : Screen("profile_screen")
}
