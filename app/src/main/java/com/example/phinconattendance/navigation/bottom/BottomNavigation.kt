package com.example.phinconattendance.navigation.bottom

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.screen.main.history.HistoryScreen
import com.example.phinconattendance.screen.main.ProfileScreen
import com.example.phinconattendance.screen.main.home.HomeScreen
import com.example.phinconattendance.screen.main.home.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalPagerApi
@Composable
fun BottomNavigation (databaseViewModel: DatabaseViewModel,
                      navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = BottomScreen.Home.screen_route){
        composable(BottomScreen.Home.screen_route){
            HomeScreen()
        }
        composable(BottomScreen.History.screen_route){
            HistoryScreen(databaseViewModel)
        }
        composable(BottomScreen.Profile.screen_route){
            ProfileScreen(databaseViewModel,navController = navController)
        }
    }
}