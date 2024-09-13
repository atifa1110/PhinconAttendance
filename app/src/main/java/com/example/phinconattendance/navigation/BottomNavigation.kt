package com.example.phinconattendance.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.phinconattendance.navigation.bottom.BottomScreen
import com.example.phinconattendance.screen.main.history.HistoryRoute
import com.example.phinconattendance.screen.main.home.HomeRoute
import com.example.phinconattendance.screen.main.profile.ProfileRoute


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigation (
    navController: NavHostController,
    onNavigateToLogin : () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = BottomScreen.Home.screen_route
    ){
        composable(BottomScreen.Home.screen_route){
            HomeRoute()
        }
        composable(BottomScreen.History.screen_route){
            HistoryRoute()
        }
        composable(BottomScreen.Profile.screen_route){
            ProfileRoute(
                onNavigateToLogin = onNavigateToLogin
            )
        }
    }
}