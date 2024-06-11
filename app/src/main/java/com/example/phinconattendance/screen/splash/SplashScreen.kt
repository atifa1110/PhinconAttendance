package com.example.phinconattendance.screen.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.navigation.NavHostController
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.ui.theme.BrokenWhite
import com.example.phinconattendance.ui.theme.DarkBlue
import kotlinx.coroutines.delay
import com.example.phinconattendance.R

@Composable
fun AnimatedSplashScreen(navController : NavHostController){
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navController.popBackStack()
        navController.navigate(Screen.OnBoarding.route)
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
                .size(300.dp,80.dp)
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

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkView(){
    Splash(alpha = 1f)
}