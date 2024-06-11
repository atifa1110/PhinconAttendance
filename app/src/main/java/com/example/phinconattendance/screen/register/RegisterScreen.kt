package com.example.phinconattendance.screen.register

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.phinconattendance.R
import com.example.phinconattendance.component.*
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.data.database.DatabaseViewModel
import com.example.phinconattendance.data.model.UserResponse
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.ui.theme.ButtonYellow
import com.example.phinconattendance.ui.theme.ButtonYellowText
import com.example.phinconattendance.ui.theme.CircleColor
import com.example.phinconattendance.ui.theme.LoginPrimary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(authViewModel : AuthViewModel = hiltViewModel(),
                   dataViewModel : DatabaseViewModel = hiltViewModel(),
                   navController: NavHostController){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var name = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirm = remember { mutableStateOf("") }

    val isErrorName = remember { mutableStateOf(false) }
    val isErrorNameMessage = remember { mutableStateOf("") }

    val isErrorEmail = remember { mutableStateOf(false) }
    val isErrorEmailMessage = remember { mutableStateOf("") }

    val isErrorPassword = remember { mutableStateOf(false) }
    val isErrorPasswordMessage = remember { mutableStateOf("") }

    val isErrorConfirm = remember { mutableStateOf(false) }
    val isErrorConfirmMessage = remember { mutableStateOf("") }

    val currentUser = authViewModel.currentUser

    var isDialog by remember { mutableStateOf(false) }

    if (isDialog)
        CommonDialog()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(LoginPrimary)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd) {
                Canvas(modifier = Modifier
                    .size(300.dp)
                    .clipToBounds()) {
                    drawArc(color = CircleColor, 90f, 180f,
                        useCenter = true,
                        topLeft = Offset(150.dp.toPx(), -150.dp.toPx()),
                        size = Size(size.width, size.height),
                        blendMode = BlendMode.Luminosity
                    )
                }
            }

            Column(modifier = Modifier.fillMaxSize()) {

                Spacer(modifier = Modifier.height(50.dp))

                TextWhiteComponent(
                    value = "Create Your Account",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))

                TextWhiteComponent(
                    value = "Please fill email, full name, password, & repeat password to register your app",
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp)

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
                    .padding(horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top) {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        elevation = 5.dp) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top) {
                            TextComponent(
                                value = "Email")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextFieldComponent(
                                email,
                                labelValue = "Email",
                                onTextChanged = {},
                                isErrorEmail.value,
                                isErrorEmailMessage)

                            Spacer(modifier = Modifier.height(20.dp))
                            
                            TextComponent(
                                value = "Full Name")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextFieldComponent(
                                name,
                                labelValue = "Employee Full Name",
                                onTextChanged = {},
                                isErrorName.value,
                                isErrorNameMessage)

                            Spacer(modifier = Modifier.height(20.dp))

                            TextComponent(
                                value = "Password")
                            Spacer(modifier = Modifier.height(8.dp))
                            PasswordTextFieldComponent(
                                password,
                                labelValue = "********",
                                onTextSelected = {},
                                isErrorPassword.value,
                                isErrorPasswordMessage)

                            Spacer(modifier = Modifier.height(20.dp))

                            TextComponent(
                                value = "Repeat Password")
                            Spacer(modifier = Modifier.height(8.dp))
                            PasswordTextFieldComponent(
                                confirm,
                                labelValue = "********",
                                onTextSelected = {},
                                isErrorConfirm.value,
                                isErrorConfirmMessage)

                            Spacer(modifier = Modifier.height(40.dp))

                            Column(modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Bottom ) {
                                Column(modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Bottom) {
                                    ButtonComponent(
                                        value = "Register",
                                        ButtonYellow,
                                        ButtonYellowText,
                                        onButtonClicked = {
                                            when (RegisterUtils().registerFormatValidation(
                                                email.value, password.value, confirm.value)) {
                                                1->{
                                                    scope.launch(Dispatchers.Main) {
                                                        authViewModel.registerUser(name.value,email.value,password.value).collect {
                                                            isDialog = when (it) {
                                                                is ResultState.Success -> {
                                                                    context.showMsg(it.data)
                                                                    if (currentUser != null) {
                                                                        scope.launch(Dispatchers.Main) {
                                                                            dataViewModel.insert(
                                                                                it.data,
                                                                                UserResponse.User(name.value, email.value,"","",0)
                                                                            ).collect {
                                                                                when (it) {
                                                                                    is ResultState.Success -> {
                                                                                        context.showMsg(msg = it.data)
                                                                                    }
                                                                                    is ResultState.Failure -> {
                                                                                        context.showMsg(msg = it.msg.toString())
                                                                                    }
                                                                                    else -> {}
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    navController.navigate(Screen.Home.route) {
                                                                        popUpTo(Screen.Login.route) {
                                                                            inclusive = true
                                                                        }
                                                                    }
                                                                    false
                                                                }
                                                                is ResultState.Failure -> {
                                                                    context.showMsg(it.msg.toString())
                                                                    false
                                                                }
                                                                is ResultState.Loading -> {
                                                                    true
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                2->{
                                                    isErrorEmail.value = true
                                                    isErrorEmailMessage.value = context.getString(R.string.email_empty)
                                                    isErrorPasswordMessage.value = ""
                                                }
                                                3->{
                                                    isErrorEmail.value = true
                                                    isErrorEmailMessage.value = context.getString(R.string.email_long)
                                                    isErrorPasswordMessage.value = ""
                                                }
                                                4->{
                                                    isErrorEmail.value = true
                                                    isErrorEmailMessage.value = context.getString(R.string.email_wrong)
                                                    isErrorPasswordMessage.value = ""
                                                }
                                                5->{
                                                    isErrorEmail.value = false
                                                    isErrorEmailMessage.value = ""
                                                    isErrorPassword.value = true
                                                    isErrorPasswordMessage.value = context.getString(R.string.password_empty)
                                                    isErrorConfirm.value = true
                                                    isErrorConfirmMessage.value = context.getString(R.string.password_empty)
                                                }
                                                6->{
                                                    isErrorEmail.value = false
                                                    isErrorEmailMessage.value = ""
                                                    isErrorPassword.value = true
                                                    isErrorPasswordMessage.value = context.getString(R.string.password_matched)
                                                    isErrorConfirm.value = true
                                                    isErrorConfirmMessage.value = context.getString(R.string.password_matched)
                                                }
                                                7->{
                                                    isErrorEmail.value = false
                                                    isErrorEmailMessage.value = ""
                                                    isErrorPassword.value = true
                                                    isErrorPasswordMessage.value = context.getString(R.string.password_short)
                                                    isErrorConfirm.value = true
                                                    isErrorConfirmMessage.value = context.getString(R.string.password_short)
                                                }
                                            }
                                            Log.d("SignUpButton",name.value)
                                            Log.d("SignUpButton",email.value)
                                            Log.d("SignUpButton",password.value)
                                            //Log.d("SignUpButton", currentUser!!.uid)
                                        },
                                        isEnabled = true)

                                    Spacer(modifier = Modifier.height(20.dp))

                                    ClickableLoginTextComponent(
                                        true,
                                        onTextSelected = {})
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


