package com.example.phinconattendance.navigation.bottom

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.phinconattendance.ui.theme.unselectedColor

@Composable
fun RowScope.BottomItem(
    screen: BottomScreen,
    currentDestination: String?,
    navController: NavHostController,
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.title) },
        selected = currentDestination == screen.screen_route,
        label = { Text(text = screen.title) },
        unselectedContentColor = unselectedColor,
        onClick = {
            if(currentDestination != screen.screen_route) {
                navController.navigate(screen.screen_route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true // Avoid multiple copies of the same destination
                }
            }
        }
    )
}