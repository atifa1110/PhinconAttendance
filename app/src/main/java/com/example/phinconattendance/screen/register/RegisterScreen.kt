package com.example.phinconattendance.screen.register

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.phinconattendance.R
import com.example.phinconattendance.component.ButtonComponent
import com.example.phinconattendance.component.ClickableLoginTextComponent
import com.example.phinconattendance.component.PasswordTextFieldComponent
import com.example.phinconattendance.component.TextComponent
import com.example.phinconattendance.component.TextFieldComponent
import com.example.phinconattendance.component.TextWhiteComponent
import com.example.phinconattendance.ui.theme.ButtonYellow
import com.example.phinconattendance.ui.theme.ButtonYellowText
import com.example.phinconattendance.ui.theme.CircleColor
import com.example.phinconattendance.ui.theme.LoginPrimary
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme
@Composable
fun RegisterRoute(
    onNavigateToLogin : () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: RegisterViewModel =  hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if(uiState.isLoading){
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
        }
    }

    RegisterScreen(
        name = uiState.name,
        nameError = uiState.nameError,
        email = uiState.email,
        emailError = uiState.emailError,
        password = uiState.password,
        passwordError = uiState.passwordError,
        confirm = uiState.confirmPassword,
        confirmError = uiState.confirmError,
        isRegister = uiState.isRegister,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRegister = viewModel::registerEmailAndPassword,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToHome = onNavigateToHome
    )
}
@Composable
fun RegisterScreen(
    name : String,
    nameError : String?,
    email : String,
    emailError : String?,
    password : String,
    passwordError : String?,
    confirm : String,
    confirmError : String?,
    isRegister : Boolean,
    onNameChange : (String) -> Unit,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String,String) -> Unit,
    onRegister : () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(LoginPrimary)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth(),
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

            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(32.dp))
                TextWhiteComponent(
                    value = "Create Your Account",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextWhiteComponent(
                    value = "Please fill email, full name, password, & repeat password to register your app",
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp
                )

                Card(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, start = 5.dp, end = 5.dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    elevation = 5.dp
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                    ) {
                        TextComponent(value = "Email")
                        Spacer(modifier = Modifier.height(8.dp))
                        TextFieldComponent(
                            text = email,
                            textError = emailError,
                            labelName = R.string.email,
                            onTextChanged = onEmailChange
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextComponent(value = "Full Name")
                        Spacer(modifier = Modifier.height(8.dp))
                        TextFieldComponent(
                            text = name,
                            textError = nameError,
                            labelName = R.string.full_name,
                            onTextChanged = onNameChange
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextComponent(value = "Password")
                        Spacer(modifier = Modifier.height(8.dp))
                        PasswordTextFieldComponent(
                            text = password,
                            textError = passwordError,
                            labelName = R.string.password,
                            onTextChanged = {}
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextComponent(value = "Repeat Password")
                        Spacer(modifier = Modifier.height(8.dp))
                        PasswordTextFieldComponent(
                            text = confirm,
                            textError = confirmError,
                            labelName = R.string.repeat_password,
                            onTextChanged = {}
                        )

                        Column(modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            ButtonComponent(
                                value = R.string.register,
                                background = ButtonYellow,
                                content = ButtonYellowText,
                                onButtonClicked = {
                                    onRegister()
                                    if (isRegister) {
                                        onNavigateToHome()
                                    }
                                },
                                isEnabled = nameError == null &&
                                        emailError == null && passwordError == null
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            ClickableLoginTextComponent(
                                tryingToLogin = true,
                                onTextSelected = {
                                    onNavigateToLogin()
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    PhinconAttendanceTheme {
        RegisterScreen(
            name = "",
            nameError = "Name is Empty",
            email = "atifafiorenza24@gmail.com",
            emailError = null,
            password = "12345678",
            passwordError = null,
            confirm = "12345678",
            confirmError = null,
            isRegister = false,
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {_,_ ->},
            onRegister = {},
            onNavigateToLogin = {},
            onNavigateToHome = {}
        )
    }
}
