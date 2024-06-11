package com.example.phinconattendance.screen.forgot

import android.app.AlertDialog
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.phinconattendance.component.*
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(viewModel: AuthViewModel = hiltViewModel(),
                         navController: NavHostController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email = remember { mutableStateOf("") }
//    var password = remember { mutableStateOf("") }
//    var confirm = remember { mutableStateOf("") }

    val isErrorEmail = remember { mutableStateOf(false) }
    val isErrorEmailMessage = remember { mutableStateOf("") }

//    val isErrorPassword = remember { mutableStateOf(false) }
//    val isErrorPasswordMessage = remember { mutableStateOf("") }
//
//    val isErrorConfirm = remember { mutableStateOf(false) }
//    val isErrorConfirmMessage = remember { mutableStateOf("") }

    var isDialogLoading by remember { mutableStateOf(false) }
    val isDialogSuccess = remember { mutableStateOf(false) }


    if(isDialogSuccess.value)
        AlertDialog(isDialogSuccess, navController)

    if (isDialogLoading)
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
                    value = "Forgot Password",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))

                TextWhiteComponent(
                    value = "Please fill Email, Password, & New Password to reset your account",
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

//                            TextComponent(
//                                value = "Password")
//
//                            PasswordTextFieldComponent(
//                                password,
//                                labelValue = "********",
//                                onTextSelected = {},
//                                isErrorPassword.value,
//                                isErrorPasswordMessage)
//
//                            Spacer(modifier = Modifier.height(20.dp))

//                            TextComponent(
//                                value = "New Password")
//
//                            PasswordTextFieldComponent(
//                                confirm,
//                                labelValue = "********",
//                                onTextSelected = {},
//                                isErrorConfirm.value,
//                                isErrorConfirmMessage)


                            Column(modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Bottom                                ) {
                                Column(modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Bottom) {
                                    ButtonComponent(
                                        value = "Reset Password",
                                        ButtonYellow,
                                        ButtonYellowText,
                                        onButtonClicked = {
                                            scope.launch(Dispatchers.Main) {
                                                viewModel.resetPassword(email.value).collect {
                                                      when(it){
                                                        is ResultState.Success -> {
                                                            context.showMsg(it.data)
                                                            isDialogLoading = false
                                                            isDialogSuccess.value = true
                                                        }
                                                        is ResultState.Failure -> {
                                                            context.showMsg(it.msg.toString())
                                                            isDialogLoading = false
                                                            isDialogSuccess.value = false
                                                        }
                                                        is ResultState.Loading -> {
                                                            isDialogLoading = true
                                                            isDialogSuccess.value = false
                                                        }
                                                    }
                                                }
                                            }
                                        },isEnabled = true)

                                    Spacer(modifier = Modifier.height(20.dp))

                                    ClickableLoginTextComponent(
                                        true,
                                        onTextSelected = {
                                            navController.navigate(Screen.Login.route)
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialog(isDialogSuccess:MutableState<Boolean>,navController: NavHostController){

    if (isDialogSuccess.value) {
        AlertDialog(
            onDismissRequest = {
                isDialogSuccess.value = false
            },
            title = {
                Text(text = "Send Email is Success", modifier = Modifier.fillMaxWidth())
            },
            text = {
                Text(text = "Please check your email to reset your password")
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    ButtonComponent(
                        value = "Login",
                        background = ButtonYellow,
                        text = ButtonYellowText,
                        onButtonClicked = {
                            //isDialogSuccess.value = false
                            navController.navigate(Screen.Login.route)
                        },true)
                }
            }
        )
    }
}

