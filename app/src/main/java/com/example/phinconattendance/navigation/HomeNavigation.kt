package com.example.phinconattendance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.phinconattendance.navigation.bottom.BottomScreen
import com.example.phinconattendance.screen.main.ProfileScreen
import com.example.phinconattendance.screen.main.history.HistoryScreen
import com.example.phinconattendance.screen.main.home.HomeScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomScreen.Home.screen_route
    ) {
        composable(route = BottomScreen.Home.screen_route){
            //HomeScreen()
        }
        composable(route = BottomScreen.History.screen_route){
            //HistoryScreen(databaseViewModel)
        }
        composable(route = BottomScreen.Profile.screen_route){
            //ProfileScreen(databaseViewModel,navController = navController)
        }
    }
}