package com.example.phinconattendance.navigation.tab

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phinconattendance.component.CommonDialog
import com.example.phinconattendance.component.TextComponent
import com.example.phinconattendance.component.showMsg
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.data.database.DatabaseAttendanceViewModel
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.data.model.*
import com.example.phinconattendance.screen.main.history.AttendanceListItem
import com.example.phinconattendance.screen.main.home.ClickListItem
import com.example.phinconattendance.screen.main.home.PlaceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.days

sealed class TabItem(
    val title: String,
    val screenToLoad: @Composable () -> Unit
) {
    object Day : TabItem( "Day", {
        DayScreenForTab()
    })
    object Week : TabItem("Week", {
        WeekScreenForTab()
    })
    object Month : TabItem("Month", {
        MonthScreenForTab()
    })
    object Year : TabItem("Year", {
        YearScreenForTab()
    })
}

@Composable
fun DayScreenForTab() {

    var key by remember { mutableStateOf (1) }
    val viewModel :DatabaseAttendanceViewModel = hiltViewModel()
    val res = viewModel.res.value
    val context = LocalContext.current

    LaunchedEffect(key1 = key) {
        viewModel.getAttendance()
    }

    if(res.isLoading)
        CommonDialog()

    if (res.error.isNotEmpty()) {
        context.showMsg(res.error)
    }

    if (res.item.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                //TextComponent("Day Screen")
                val attendanceList = res.item
                AttendanceDayList(attendanceList)
            }
        }
    }
}

@Composable
fun WeekScreenForTab() {
    val viewModel :DatabaseAttendanceViewModel = hiltViewModel()
    val res = viewModel.res.value
    val context = LocalContext.current

    if(res.isLoading)
        CommonDialog()

    if (res.error.isNotEmpty()) {
        context.showMsg(res.error)
    }

    if (res.item.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                //TextComponent("Week Screen")
                val attendanceList = res.item
                AttendanceWeekList(attendanceList)
            }
        }
    }
}

@Composable
fun MonthScreenForTab() {
    val viewModel :DatabaseAttendanceViewModel = hiltViewModel()
    val res = viewModel.res.value
    val context = LocalContext.current

    if(res.isLoading)
        CommonDialog()

    if (res.error.isNotEmpty()) {
        context.showMsg(res.error)
    }

    if (res.item.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                //TextComponent("Month Screen")
                val attendanceList = res.item
                AttendanceMonthList(attendanceList)
            }
        }
    }
}

@Composable
fun YearScreenForTab() {
    val viewModel :DatabaseAttendanceViewModel = hiltViewModel()
    val res = viewModel.res.value
    val context = LocalContext.current

    if(res.isLoading)
        CommonDialog()

    if (res.error.isNotEmpty()) {
        context.showMsg(res.error)
    }

    if (res.item.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                //TextComponent("Year Screen")
                val attendanceList = res.item
                AttendanceYearList(attendanceList)
            }
        }
    }
}

@Composable
fun AttendanceDayList(items : List<AttendanceResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(items, key = {it.key!!}) { item ->
                var boolean = Time().convertToHour(item.item?.time)<24
                Log.d("AttendanceTime",boolean.toString())
                if(boolean) {
                    AttendanceListItem(item)
                }
            }
        }
    }
}

@Composable
fun AttendanceWeekList(items : List<AttendanceResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(items, key = {it.key!!}) {item ->
                var boolean = Time().convertToDay(item.item?.time) in 1..7
                Log.d("AttendanceTime",boolean.toString())
                if(boolean) {
                    AttendanceListItem(item)
                }
            }
        }
    }
}

@Composable
fun AttendanceMonthList(items : List<AttendanceResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(items, key = {it.key!!}) {item ->
                var boolean = Time().convertToDay(item.item?.time) in 7..30
                Log.d("AttendanceTime",boolean.toString())
                if(boolean) {
                    AttendanceListItem(item)
                }
            }
        }
    }
}

@Composable
fun AttendanceYearList(items : List<AttendanceResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(items, key = {it.key!!}) {item ->
                var boolean = Time().convertToDay(item.item?.time)in 30..365
                Log.d("AttendanceTime",boolean.toString())
                if(boolean) {
                    AttendanceListItem(item)
                }
            }
        }
    }
}