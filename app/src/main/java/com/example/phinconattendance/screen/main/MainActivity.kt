package com.example.phinconattendance.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.navigation.EntryNavigation
import com.example.phinconattendance.navigation.RootNavigationGraph
import com.example.phinconattendance.navigation.tab.DayScreenForTab
import com.example.phinconattendance.navigation.tab.TabLayoutScreen
import com.example.phinconattendance.screen.forgot.ForgotPasswordScreen
import com.example.phinconattendance.screen.login.LoginScreen
import com.example.phinconattendance.screen.main.home.HomeScreen
import com.example.phinconattendance.screen.main.home.HomeViewModel
import com.example.phinconattendance.screen.register.RegisterScreen
import com.example.phinconattendance.screen.splash.SplashViewModel
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel
    private val authViewModel by viewModels<AuthViewModel>()
    private val dataViewModel by viewModels<DatabaseViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen().setKeepOnScreenCondition {
//            !splashViewModel.isLoading.value
//        }

        setContent {
            PhinconAttendanceTheme {
                //screen splash screen begin
                val screen by splashViewModel.startDestination
                //Navigation Controller
                val navController = rememberNavController()
                EntryNavigation(navController,authViewModel, dataViewModel,screen)
                //RootNavigationGraph(databaseViewModel = dataViewModel, navController = navController)
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