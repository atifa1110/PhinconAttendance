package com.example.phinconattendance.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.screen.boarding.OnBoardingScreen
import com.example.phinconattendance.screen.forgot.ForgotPasswordScreen
import com.example.phinconattendance.screen.login.LoginScreen
import com.example.phinconattendance.screen.main.MainScreen
import com.example.phinconattendance.screen.main.home.HomeViewModel
import com.example.phinconattendance.screen.register.RegisterScreen
import com.example.phinconattendance.screen.splash.AnimatedSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun EntryNavigation(navController: NavHostController,
                  authViewModel: AuthViewModel,
                  databaseViewModel: DatabaseViewModel,
                  startDestination: String){
    NavHost(navController = navController,
        startDestination = Screen.Splash.route){
        composable(route = Screen.Splash.route){
            AnimatedSplashScreen(navController)
        }
        composable(route = Screen.OnBoarding.route){
            OnBoardingScreen(navController)
        }
        composable(route = Screen.Login.route){
            LoginScreen(authViewModel,navController)
        }
        composable(route = Screen.Register.route){
            RegisterScreen(authViewModel,databaseViewModel,navController)
        }
        composable(route = Screen.Home.route){
            MainScreen(databaseViewModel,navController = navController)
        }
        composable(route = Screen.Forgot.route){
            ForgotPasswordScreen(authViewModel,navController)
        }
    }
}
