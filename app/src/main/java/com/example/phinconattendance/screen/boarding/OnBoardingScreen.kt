package com.example.phinconattendance.screen.boarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.ui.theme.*
import com.google.accompanist.pager.*

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(navController : NavHostController,
                     onBoardingViewModel: OnBoardingViewModel = hiltViewModel()){

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second, OnBoardingPage.Third)

    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true) {
        onBoardingViewModel.readOnBoardingState()
        if(onBoardingViewModel.startDestination.equals("Complete")){
            navController.navigate(Screen.Login.route)
        }
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter){
        SkipButton(){

        }
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card()
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                HorizontalPager(modifier = Modifier.weight(10f),
                    count = 3, state = pagerState,
                    verticalAlignment = Alignment.CenterVertically
                ) { position ->
                    PagerScreen(onBoardingPage = pages[position])
                }
                HorizontalPagerIndicator(modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                    pagerState = pagerState)

                Row(modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 50.dp)
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    LoginButton(){
                        //will save boarding state to complete
                        onBoardingViewModel.saveOnBoardingState(completed = true)
                        //can't go back to previous page

                        navController.popBackStack()
                        //navigate to screen login
                        navController.navigate(Screen.Login.route)
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    SignUpButton(pagerState = pagerState){
                        //navigate to screen register
                        navController.navigate(Screen.Register.route)
                    }
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage){
    Column(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(modifier = Modifier
            .size(250.dp, 250.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
            text = onBoardingPage.title,
            fontSize = 18.sp,
            color = TitleColor,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
            text = onBoardingPage.description,
            fontSize = 13.sp,
            color = DescriptionColor,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun Card() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            elevation = 5.dp)
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ){
                //Button()

                Spacer(modifier = Modifier
                    .width(134.dp)
                    .height(5.dp)
                    .background(Spacer))
            }
        }
    }
}


@ExperimentalPagerApi
@Composable
fun LoginButton(onClick: () -> Unit){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, backgroundColor = Blue),
        modifier = Modifier
            .width(width = 160.dp)
            .height(48.dp),
        elevation = ButtonDefaults.elevation(30.dp))
    {
        Text(text = "Login", fontSize = 16.sp)
    }
}

@ExperimentalPagerApi
@Composable
fun SignUpButton(pagerState: PagerState, onClick: () -> Unit){
    Button(
        enabled = pagerState.currentPage==0,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, backgroundColor = Blue
        ),
        modifier = Modifier
            .width(width = 160.dp)
            .height(48.dp),
        elevation = ButtonDefaults.elevation(30.dp)
    )
    {
        Text(text = "Sign Up", fontSize = 16.sp)
    }
}

@Composable
fun SkipButton(onClick: () -> Unit){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onClick, modifier = Modifier.padding(top = 24.dp, end = 24.dp)) {
            Text(text = "SKIP", fontSize = 16.sp , color = SkipColor)
        }
    }
}
