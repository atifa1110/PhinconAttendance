package com.example.phinconattendance.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.phinconattendance.screen.boarding.OnBoardingRoute
import com.example.phinconattendance.screen.forgot.ForgotPasswordRoute
import com.example.phinconattendance.screen.login.LoginRoute
import com.example.phinconattendance.screen.main.main.MainScreen
import com.example.phinconattendance.screen.register.RegisterRoute
import com.example.phinconattendance.screen.splash.AnimatedSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MainNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination // start destination = splash screen
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToBoarding = {
                    navController.navigate(Screen.OnBoarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.OnBoarding.route) {
            OnBoardingRoute(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(route = Screen.Login.route) {
            LoginRoute(
                onNavigateToForgotPassword = { navController.navigate(Screen.Forgot.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToHome = {
                    // when login button click
                    // navigate to login screen and erase login screen from stack
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterRoute(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Home.route) {
            MainScreen(
                // when logout button click
                // navigate to login screen and erase home screen from stack
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Forgot.route) {
            ForgotPasswordRoute(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
    }
}
