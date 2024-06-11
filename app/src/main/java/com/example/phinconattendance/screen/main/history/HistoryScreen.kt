package com.example.phinconattendance.screen.main.history

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phinconattendance.component.TextBigComponent
import com.example.phinconattendance.navigation.tab.TabLayoutScreen
import com.example.phinconattendance.screen.main.home.AppBar
import com.example.phinconattendance.ui.theme.Background
import com.example.phinconattendance.ui.theme.CircleColor
import com.example.phinconattendance.ui.theme.MainColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.example.phinconattendance.R
import com.example.phinconattendance.data.database.DatabaseViewModel

@Composable
@ExperimentalPagerApi
fun HistoryScreen(databaseViewModel: DatabaseViewModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Background)) {
        Box(modifier = Modifier.fillMaxSize()){
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MainColor),
                contentAlignment = Alignment.TopEnd) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(300.dp)
                            .clipToBounds()
                    ) {
                        drawArc(
                            color = CircleColor, 90f, 180f,
                            useCenter = true,
                            topLeft = Offset(150.dp.toPx(), -150.dp.toPx()),
                            size = Size(size.width, size.height),
                            blendMode = BlendMode.Luminosity
                        )
                    }
                }

            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)){
                Spacer(modifier = Modifier.height(10.dp))
                AppBar("Attendance History",R.drawable.ic_notification)
                Spacer(modifier = Modifier.height(40.dp))
                CardHistory(databaseViewModel)
            }
        }
    }
}

@Composable
@ExperimentalPagerApi
fun CardHistory(databaseViewModel: DatabaseViewModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(modifier = Modifier.height(10.dp))
            TextBigComponent(value = "Logs")
            Spacer(modifier = Modifier.height(10.dp))
            TabLayoutScreen(databaseViewModel)
        }

    }
}
