package com.example.phinconattendance.screen.login

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.phinconattendance.R
import com.example.phinconattendance.component.*
import com.example.phinconattendance.data.auth.AuthViewModel
import com.example.phinconattendance.data.auth.ResultState
import com.example.phinconattendance.navigation.Screen
import com.example.phinconattendance.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel(),
                navController: NavHostController){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    val isErrorEmail = remember { mutableStateOf(false) }
    val isErrorEmailMessage = remember { mutableStateOf("") }

    val isErrorPassword = remember { mutableStateOf(false) }
    val isErrorPasswordMessage = remember { mutableStateOf("") }

    var isDialog by remember { mutableStateOf(false) }

    if (isDialog)
        CommonDialog()

    LaunchedEffect(key1 = true) {
        if(viewModel.currentUser!=null){
            navController.navigate(Screen.Home.route)
        }
    }

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
                    value = "Welcome To Login",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))

                TextWhiteComponent(
                    value = stringResource(R.string.fill_data),
                    textAlign = TextAlign.Justify,
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
                            verticalArrangement = Arrangement.Top
                        ) {
                            TextComponent(
                                value = "Email"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextFieldComponent(
                                input = email,
                                labelValue = "Email",
                                onTextChanged = {},
                                errorStatus = isErrorEmail.value,
                                errorMessage = isErrorEmailMessage
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            TextComponent(
                                value = "Password"
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            PasswordTextFieldComponent(
                                password = password,
                                labelValue = "********",
                                onTextSelected = {},
                                errorStatus = isErrorPassword.value,
                                errorMessage = isErrorPasswordMessage
                            )

                            TextButtonComponent(
                                value = "Forgot Password?",
                                onButtonClicked = {
                                    navController.navigate(Screen.Forgot.route)
                                })

                            Spacer(modifier = Modifier.height(40.dp))

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    ButtonComponent(
                                        value = "Login Now",
                                        ButtonYellow,
                                        ButtonYellowText,
                                        onButtonClicked = {
                                            when (LoginUtils().loginFormatValidation(
                                                email.value, password.value
                                            )) {
                                                1 -> {
                                                    scope.launch(Dispatchers.Main) {
                                                        viewModel.loginUser(
                                                            email.value,
                                                            password.value
                                                        ).collect {
                                                            isDialog = when (it) {
                                                                is ResultState.Success -> {
                                                                    context.showMsg(it.data)
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
                                                2 -> {
                                                    isErrorEmail.value = true
                                                    isErrorEmailMessage.value =
                                                        context.getString(R.string.email_empty)
                                                }
                                                3 -> {
                                                    isErrorEmail.value = true
                                                    isErrorEmailMessage.value =
                                                        context.getString(R.string.email_long)
                                                }
                                                4 -> {
                                                    isErrorEmail.value = true
                                                    isErrorEmailMessage.value =
                                                        context.getString(R.string.email_wrong)
                                                }
                                                5 -> {
                                                    isErrorEmail.value = false
                                                    isErrorEmailMessage.value = ""
                                                    isErrorPassword.value = true
                                                    isErrorPasswordMessage.value =
                                                        context.getString(R.string.password_short)
                                                }
                                                6 -> {
                                                    isErrorEmail.value = false
                                                    isErrorEmailMessage.value = ""
                                                    isErrorPassword.value = true
                                                    isErrorPasswordMessage.value =
                                                        context.getString(R.string.password_empty)
                                                }
                                            }
                                        },
                                        isEnabled = true
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))

                                    ClickableLoginTextComponent(
                                        false,
                                        onTextSelected = {
                                            navController.navigate(Screen.Register.route)
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
@Preview(showBackground = true)
fun LoginPreview(){
    //LoginScreen(rememberNavController())
}