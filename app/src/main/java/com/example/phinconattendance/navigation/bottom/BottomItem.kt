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
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    //RowScope.BottomNavigationItem
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigationItem(
        icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.title) },
//        selected = currentDestination?.hierarchy?.any {
//            it.route == screen.screen_route
//        } == true,
        selected = screen.screen_route == backStackEntry.value?.destination?.route,
        label = { Text(text = screen.title) },
        unselectedContentColor = unselectedColor,
        onClick = {
            navController.navigate(screen.screen_route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}