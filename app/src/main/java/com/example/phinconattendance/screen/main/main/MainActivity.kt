package com.example.phinconattendance.screen.main.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.phinconattendance.navigation.MainNavigation
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.screen.main.home.HomeViewModel
import com.example.phinconattendance.screen.splash.SplashViewModel
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhinconAttendanceTheme {
                val navController = rememberNavController()
                MainNavigation(navController = navController, startDestination = Screen.Splash.route)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PhinconAttendanceTheme {

    }
}