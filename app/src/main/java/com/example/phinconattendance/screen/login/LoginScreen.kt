package com.example.phinconattendance.screen.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.phinconattendance.R
import com.example.phinconattendance.component.*
import com.example.phinconattendance.ui.theme.*

@Composable
fun LoginRoute(
    onNavigateToForgotPassword : () -> Unit,
    onNavigateToRegister : () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel =  hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        email = uiState.email,
        emailError = uiState.emailError,
        password = uiState.password,
        passwordError = uiState.passwordError,
        isLoading = uiState.isLoading,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLogin = {
            viewModel.loginAccount()
            if(uiState.isLogin){
                onNavigateToHome()
                viewModel.saveOnLoginState(true)
            }else{
                viewModel.onEmailChange("")
                viewModel.onPasswordChange("")
            }
        },
        onNavigateToForgotPassword = onNavigateToForgotPassword,
        onNavigateToRegister = onNavigateToRegister,
    )
}


@Composable
fun LoginScreen(
    email : String,
    emailError : String?,
    password : String,
    passwordError : String?,
    isLoading : Boolean,
    onEmailChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onLogin : () -> Unit,
    onNavigateToForgotPassword : () -> Unit,
    onNavigateToRegister : () -> Unit,
) {
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isLoading){
            CircularProgressIndicator(color = Blue)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LoginPrimary)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
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

                    Column(modifier = Modifier.fillMaxSize()) {

                        Spacer(modifier = Modifier.height(32.dp))

                        TextWhiteComponent(
                            value = "Welcome To Login",
                            textAlign = TextAlign.Start,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextWhiteComponent(
                            value = stringResource(R.string.fill_data),
                            textAlign = TextAlign.Justify,
                            fontSize = 14.sp
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp, start = 5.dp, end = 5.dp),
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                            elevation = 5.dp
                        ) {
                            Column(modifier = Modifier.fillMaxSize()
                                    .padding(24.dp),
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

                                TextComponent(value = "Password")
                                Spacer(modifier = Modifier.height(8.dp))
                                PasswordTextFieldComponent(
                                    text = password,
                                    textError = passwordError,
                                    labelName = R.string.password,
                                    onTextChanged = onPasswordChange
                                )

                                TextButtonComponent(
                                    value = R.string.forgot_password,
                                    onButtonClicked = onNavigateToForgotPassword
                                )

                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    ButtonComponent(
                                        value = R.string.login_now,
                                        background = ButtonYellow,
                                        content = ButtonYellowText,
                                        onButtonClicked = {
                                            onLogin()
                                        },
                                        isEnabled = email.isNotEmpty() && emailError == null && password.isNotEmpty() && passwordError == null
                                    )

                                    Spacer(modifier = Modifier.height(20.dp))

                                    ClickableLoginTextComponent(
                                        tryingToLogin = false,
                                        onTextSelected = {
                                            onNavigateToRegister()
                                        }
                                    )
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
    LoginScreen(
        email = "",
        emailError = "Email is Empty",
        password = "12345678",
        passwordError = null,
        isLoading = false,
        onEmailChange = {},
        onPasswordChange = {},
        onLogin = {},
        onNavigateToForgotPassword = {},
        onNavigateToRegister = {},
    )
}