package com.example.phinconattendance.screen.main

import android.annotation.SuppressLint
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.navigation.bottom.BottomNavigation
import com.example.phinconattendance.navigation.bottom.BottomNavigationCustom
import com.example.phinconattendance.screen.main.home.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalPagerApi
fun MainScreen(databaseViewModel: DatabaseViewModel,navController: NavHostController = rememberNavController()) {
    val navControll = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomAppBar (backgroundColor = Color.White){
                BottomNavigationCustom(navControll)
            }
        }
    ) {
        BottomNavigation(databaseViewModel,navControll)
    }
}