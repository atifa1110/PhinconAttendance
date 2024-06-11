package com.example.phinconattendance.screen.main

import android.app.AlertDialog
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.phinconattendance.ui.theme.*
import com.example.phinconattendance.R
import com.example.phinconattendance.component.ButtonComponent
import com.example.phinconattendance.component.CommonDialog
import com.example.phinconattendance.component.TextWhiteBigComponent
import com.example.phinconattendance.component.showMsg
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.database.DatabaseAttendanceViewModel
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.screen.main.AlertDialog


@Composable
fun ProfileScreen (viewModel: DatabaseViewModel = hiltViewModel(),navController: NavController) {

    val authViewModel : AuthViewModel  = hiltViewModel()

    val res = viewModel.resUser.value
    val context = LocalContext.current

    val name = res.item?.item?.name
    val address = res.item?.item?.address
    val employee = res.item?.item?.employee
    val currentId = authViewModel.current

    LaunchedEffect(key1 = true) {
        viewModel.getUser()
    }

    if(res.isLoading)
        CommonDialog()

    if (res.error.isNotEmpty()) {
        context.showMsg(res.error)
    }

//    if(res.item?.item!=null){
//        context.showMsg(res.item.item.name.toString())
//    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Background)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(bottomEnd = 32.dp))
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
                AppBar(text = "Profile", icon = R.drawable.ic_edit)
                Spacer(modifier = Modifier.height(20.dp))
                Card(name.toString())
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "GENERAL",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W800
                    ), color = SkipColor
                )

                CardGeneral(title = "No. Karyawan", subtitle = employee.toString(), icon = R.drawable.karyawan)
                CardGeneral(title = "Alamat", subtitle = address.toString(), icon = R.drawable.security)
                CardGeneral(title = "Change Password", subtitle = "********", icon = R.drawable.notification)
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(value = "Logout", Color.Red, Color.White,onButtonClicked = {
                    authViewModel.logout().let {
                        context.showMsg("Logout is success")
                    }
                    navController.navigate(Screen.Login.route)
                },true)
            }
        }
    }
}
@Composable
fun Card(name:String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
        shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Card(modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = R.drawable.img_1), contentDescription = "Photo Profile")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = name,
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W800,
                    fontStyle = FontStyle.Normal
                ), color = TitleColor
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "PROGRAMMER",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W500,
                    fontStyle = FontStyle.Normal
                ), color = SkipColor
            )

        }
    }
}

@Composable
fun CardGeneral(title:String,subtitle:String,icon:Int){
    Column(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
        Card(modifier = Modifier.fillMaxWidth()
            ,shape = RoundedCornerShape(8.dp), elevation = 2.dp) {
            Box(modifier = Modifier
                .fillMaxWidth()) {
                Row(modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(8.dp), elevation = 5.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(TabBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = "image"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.W800,
                            color = TitleColor,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtitle,
                            fontWeight = FontWeight.W400,
                            color = SubTitleColor,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialog(){
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val employee = remember { mutableStateOf("") }
    val address = remember{ mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {

            },
            text = {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(value = name.value, onValueChange = {
                        name.value = it
                    },
                        placeholder = { Text(text = "Name") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(value = email.value, onValueChange = {
                        email.value = it
                    },
                        placeholder = { Text(text = "Email") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(value = employee.value, onValueChange = {
                        employee.value = it
                    },
                        placeholder = { Text(text = "No.Karyawan") }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(value = address.value, onValueChange = {
                        address.value = it
                    },
                        placeholder = { Text(text = "Alamat") }
                    )

                }
            },
            buttons = {
                Row(modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {

                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        )
}

@Composable
fun AppBar(text : String, icon:Int){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
        },
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