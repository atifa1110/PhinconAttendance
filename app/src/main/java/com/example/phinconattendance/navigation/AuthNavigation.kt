package com.example.phinconattendance.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.phinconattendance.screen.forgot.ForgotPasswordScreen
import com.example.phinconattendance.screen.login.LoginScreen
import com.example.phinconattendance.screen.register.RegisterScreen
import androidx.navigation.navigation
import com.example.phinconattendance.screen.boarding.OnBoardingScreen
import com.example.phinconattendance.screen.splash.AnimatedSplashScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = AuthScreen.OnBoarding.route) {
            OnBoardingScreen(navController)
        }
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            ForgotPasswordScreen(navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Splash : AuthScreen(route = "Splash")
    object OnBoarding : AuthScreen(route = "OnBoarding")
    object Login : AuthScreen(route = "Login")
    object SignUp : AuthScreen(route = "Register")
    object Forgot : AuthScreen(route = "Forhot")
}