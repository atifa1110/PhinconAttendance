package com.example.phinconattendance.navigation.bottom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.material.BottomNavigation
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.phinconattendance.ui.theme.selectedColor

@Composable
fun BottomNavigationCustom(navController: NavHostController) {
    val items = listOf(
        BottomScreen.Home,
        BottomScreen.History,
        BottomScreen.Profile
    )

    BottomNavigation(backgroundColor = Color.White, contentColor = selectedColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        items.forEach { item ->
            currentRoute?.let {
                BottomItem(
                    screen = item,
                    currentDestination = it,
                    navController = navController
                )
            }
        }
    }
}