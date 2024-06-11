package com.example.phinconattendance.navigation.tab

import android.view.animation.Animation
import androidx.compose.animation.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalComposeApi::class, ExperimentalFoundationApi::class)
@Composable
@ExperimentalPagerApi
fun TabLayoutScreen(databaseViewModel: DatabaseViewModel) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Background,
            modifier = Modifier
                .background(Color.Transparent)
                .clip(RoundedCornerShape(10.dp)),
            indicator = {
                tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .pagerTabIndicatorOffset(pagerState, tabPositions)
                        .width(0.dp)
                        .height(0.dp)
                )
            }
        ) {
            tabs.forEachIndexed { index, item ->

                val color = remember {
                    Animatable(TabBackground)
                }
                LaunchedEffect(pagerState.currentPage == index){
                    color.animateTo(if(pagerState.currentPage == index) TabBackground else Color.Transparent)
                }
                Tab(
                    selected = index == pagerState.currentPage,
                    modifier = Modifier.background(
                        color = color.value,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    text = {
                        Text(text = item.title, style = if(pagerState.currentPage==index)
                            TextStyle(color= TextTabSelected, fontSize = 14.sp) else TextStyle(color= TextTab, fontSize = 14.sp))
                           },
                    onClick = { coroutineScope.launch {
                        pagerState.animateScrollToPage(index) } },
                )
            }
        }
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            tabs[pagerState.currentPage].screenToLoad()
        }
    }
}