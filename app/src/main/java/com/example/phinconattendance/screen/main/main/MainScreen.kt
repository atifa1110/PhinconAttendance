package com.example.phinconattendance.screen.main.main

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.phinconattendance.navigation.BottomNavigation
import com.example.phinconattendance.navigation.bottom.BottomNavigationBar
import com.example.phinconattendance.navigation.bottom.BottomScreen
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    onNavigateToLogin : () -> Unit
) {
    val nav = rememberNavController()
    HandleCustomBackPress(navController = nav)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = nav)
        }
    ) {
        Box(Modifier.padding(it)) {
            BottomNavigation(navController = nav, onNavigateToLogin = onNavigateToLogin)
        }
    }
}

@Composable
fun HandleCustomBackPress(navController: NavHostController) {
    BackHandler {
        if (navController.currentDestination?.route !=  BottomScreen.Home.screen_route) {
            // If not on the home screen, navigate back to the home screen
            navController.navigate( BottomScreen.Home.screen_route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            // If already on the home screen, perform default back action (close the app)
            navController.popBackStack()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    PhinconAttendanceTheme {
        MainScreen(
            onNavigateToLogin = {}
        )
    }
}