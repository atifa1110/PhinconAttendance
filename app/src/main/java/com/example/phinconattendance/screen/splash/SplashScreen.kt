package com.example.phinconattendance.screen.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.ui.theme.BrokenWhite
import com.example.phinconattendance.ui.theme.DarkBlue
import kotlinx.coroutines.delay
import com.example.phinconattendance.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@Composable
fun AnimatedSplashScreen(
    onNavigateToLogin : () -> Unit,
    onNavigateToBoarding : () -> Unit,
    onNavigateToHome : () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
){
    val boardingState by viewModel.onboardingState.collectAsStateWithLifecycle(initialValue = false)
    val loginState by viewModel.loginState.collectAsStateWithLifecycle(initialValue = false)

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        ), label = ""
    )

    // splash screen loading check if boarding state and login state exist or not
    // if splash screen finish than go to screen that state complete
    LaunchedEffect(true) {
        startAnimation = true
        delay(3000)
        when{
            !boardingState -> onNavigateToBoarding()
            !loginState -> onNavigateToLogin()
            else -> onNavigateToHome()
        }
    }

    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha : Float){
    Box(modifier = Modifier
        .background(if (isSystemInDarkTheme()) DarkBlue else BrokenWhite)
        .fillMaxSize(), contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id = R.drawable.ic_logo),
            modifier = Modifier
                .size(300.dp, 80.dp)
                .alpha(alpha = alpha),
            contentDescription = "Logo Icon"
        )
    }
}

@Composable
@Preview
fun SplashScreenView(){
    Splash(alpha = 1f)
}
