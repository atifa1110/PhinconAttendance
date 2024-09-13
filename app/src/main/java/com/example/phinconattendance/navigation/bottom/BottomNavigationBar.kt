package com.example.phinconattendance.navigation.bottom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.material.BottomNavigation
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.phinconattendance.ui.theme.selectedColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomScreen.Home,
        BottomScreen.History,
        BottomScreen.Profile
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = selectedColor
    ) {
        // Track the current backstack entry
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            BottomItem(
                screen = item,
                currentDestination = currentRoute,
                navController = navController
            )
        }
    }
}