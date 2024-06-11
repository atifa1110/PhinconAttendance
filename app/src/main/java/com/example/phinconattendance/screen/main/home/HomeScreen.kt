package com.example.phinconattendance.screen.main.home

import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.R
import com.example.phinconattendance.component.*
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.data.database.DatabaseAttendanceViewModel
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.PlaceResponse
import com.example.phinconattendance.data.model.fetchPlaceList
import com.example.phinconattendance.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(databaseViewModel: DatabaseAttendanceViewModel
               = hiltViewModel()) {

    val viewModel :HomeViewModel = viewModel()
    val timeValue = viewModel.currentTimeLiveData
    val dateValue = viewModel.currentDateLiveData
    var buttonEnable  = remember { mutableStateOf(false) }
    val place = databaseViewModel.listRes

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
                .padding(24.dp)){
                Spacer(modifier = Modifier.height(10.dp))
                AppBar("Your Attendance",R.drawable.ic_notification)
                Spacer(modifier = Modifier.height(40.dp))
                Card(databaseViewModel,buttonEnable,timeValue,dateValue)
                Spacer(modifier = Modifier.height(10.dp))
                TextBigComponent(value = "Location")
                val placeList = fetchPlaceList()
                PlaceList(databaseViewModel,buttonEnable,placeList)
            }
        }
    }
}

@Composable
fun AppBar(text : String, icon:Int){
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd) {
        TextWhiteBigComponent(value = text)

        Box(modifier = Modifier.fillMaxWidth(0.5f),
            contentAlignment = Alignment.CenterEnd
            ) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ){
                Image(painter = painterResource(id = icon),
                    contentDescription = "Notification" )
            }
        }
    }
}

@Composable
fun PlaceList(databaseViewModel: DatabaseAttendanceViewModel,
              buttonEnable : MutableState<Boolean>,
              items : List<PlaceResponse.Place>) {
    var selectedItemIndex by remember { mutableStateOf(-1) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(items) {item ->
                //ClickListItem(place)
                ClickListItem(item,
                    onItemClick = {
                        selectedItemIndex = items.indexOf(item)
                        if(selectedItemIndex == items.indexOf(item)) {
                            buttonEnable.value = true
                            databaseViewModel.setDataItem(item)
                            Log.d("ItemClicked",item.toString())
                        }
                    },
                    isClicked = selectedItemIndex == items.indexOf(item) )
            }
        }
    }
}

@Composable
fun Card(databaseViewModel: DatabaseAttendanceViewModel,buttonEnable: MutableState<Boolean>,
         timeValue: LiveData<String>, dateValue: LiveData<String>){

    val currentTime by timeValue.observeAsState(initial = "")
    val currentDate by dateValue.observeAsState(initial = "")

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp),
        shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd) {
                TextComponent(value = "Hour: $currentTime")

                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.End
                    ){
                        TextComponentShort(currentDate)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            CheckInButton(databaseViewModel,buttonEnable)
        }
    }
}

@Composable
fun CheckInButton(databaseViewModel: DatabaseAttendanceViewModel,
                  buttonEnable: MutableState<Boolean>){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isClicked by remember { mutableStateOf(false)}
    val place = databaseViewModel.listRes
    val buttonClick by databaseViewModel.isButtonClicked.collectAsState()
    val authViewModel : AuthViewModel = hiltViewModel()
    var isDialog by remember { mutableStateOf(false) }

    if (isDialog)
        CommonDialog()

    LaunchedEffect(key1 = 1){
        databaseViewModel.getButtonClicked()
        Log.d("HomeScreen",buttonClick.toString())
    }

    Column(modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally) {

        Button(modifier = Modifier.size(195.dp),
            onClick = {
                isClicked=!isClicked
                Log.d("ButtonClickCheck", isClicked.toString())
                if(isClicked) {
                    scope.launch(Dispatchers.Main) {
                        databaseViewModel.insertAttendance(
                            authViewModel.currentUser!!.uid,
                            System.currentTimeMillis().toString(),
                            AttendanceResponse.Attendance(
                                place.value.company, place.value.address,
                                System.currentTimeMillis(), "Check in"
                            )
                        ).collect{
                            isDialog = when (it) {
                                is ResultState.Success -> {
                                    context.showMsg(msg = it.data)
                                    false
                                }
                                is ResultState.Failure -> {
                                    context.showMsg(msg = it.msg.toString())
                                    false
                                }
                                ResultState.Loading -> {
                                    true
                                }
                            }
                        }
                    }
                }else{
                    scope.launch(Dispatchers.Main) {
                        databaseViewModel.insertAttendance(
                            authViewModel.currentUser!!.uid,
                            System.currentTimeMillis().toString(),
                            AttendanceResponse.Attendance(
                                place.value.company, place.value.address,
                                System.currentTimeMillis(), "Check out"
                            )
                        ).collect{
                            isDialog= when (it) {
                                is ResultState.Success -> {
                                    context.showMsg(
                                        msg = it.data
                                    )
                                    false
                                }
                                is ResultState.Failure -> {
                                    context.showMsg(
                                        msg = it.msg.toString()
                                    )
                                    false
                                }
                                ResultState.Loading -> {
                                    true
                                }
                            }
                        }
                    }
                }
            },
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = if(isClicked) ButtonDefaults.buttonColors(Yellow) else ButtonDefaults.buttonColors(
                Green) ,
            enabled = buttonEnable.value) {
            Box(modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if(isClicked) "Check Out" else "Check In",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W700
                )

            }
        }
    }
}