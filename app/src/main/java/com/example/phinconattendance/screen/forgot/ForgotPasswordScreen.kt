package com.example.phinconattendance.screen.forgot

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.phinconattendance.R
import com.example.phinconattendance.component.ButtonComponent
import com.example.phinconattendance.component.ClickableLoginTextComponent
import com.example.phinconattendance.component.CommonDialog
import com.example.phinconattendance.component.TextComponent
import com.example.phinconattendance.component.TextFieldComponent
import com.example.phinconattendance.component.TextWhiteComponent
import com.example.phinconattendance.ui.theme.*

@Composable
fun ForgotPasswordRoute(
    onNavigateToLogin : () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        email = uiState.email,
        onEmailChange = viewModel::onEmailChange,
        isLoading = uiState.isLoading,
        isSuccess = uiState.isSuccess,
        onResetPassword = viewModel::resetPassword,
        onNavigateToLogin = onNavigateToLogin
    )
}
@Composable
fun ForgotPasswordScreen(
    email : String,
    onEmailChange : (String) -> Unit,
    isLoading : Boolean,
    isSuccess : Boolean,
    onResetPassword : () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    var isDialogLoading by remember { mutableStateOf(false) }

    if(isSuccess)
        AlertDialog(isSuccess = isSuccess,
            onNavigateToLogin = onNavigateToLogin, onResetDialogState = {})

    if (isDialogLoading)
        CommonDialog()

    Column(modifier = Modifier.fillMaxSize().background(LoginPrimary)) {

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
                Spacer(modifier = Modifier.height(32.dp))
                TextWhiteComponent(
                    value = "Forgot Password",
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp)

                Spacer(modifier = Modifier.height(20.dp))

                TextWhiteComponent(
                    value = "Please fill Email, Password, & New Password to reset your account",
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp)

                Card(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, start = 5.dp, end = 5.dp),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    elevation = 5.dp
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    ) {
                        TextComponent(value = "Email")
                        Spacer(modifier = Modifier.height(8.dp))
                        TextFieldComponent(
                            text = email,
                            labelName = R.string.email,
                            onTextChanged = onEmailChange
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom                                ) {
                            Column(modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Bottom) {
                                ButtonComponent(
                                    value = R.string.reset_password,
                                    ButtonYellow,
                                    ButtonYellowText,
                                    onButtonClicked = {
                                        onResetPassword()
                                    },isEnabled = true)

                                Spacer(modifier = Modifier.height(20.dp))

                                ClickableLoginTextComponent(
                                    true,
                                    onTextSelected = {
                                        onNavigateToLogin()
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialog(
    isSuccess: Boolean,
    onNavigateToLogin: () -> Unit,
    onResetDialogState : () -> Unit,
){
    if (isSuccess) {
        AlertDialog(
            onDismissRequest = {},
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
                        value = R.string.login,
                        background = ButtonYellow,
                        content = ButtonYellowText,
                        onButtonClicked = {
                            onNavigateToLogin()
                        },true
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun ForgotPasswordPreview(){
    PhinconAttendanceTheme {
        ForgotPasswordScreen(
            email = "atifafiroenza24@gmail.com",
            onEmailChange = {},
            isLoading = false,
            isSuccess = true,
            onResetPassword = { /*TODO*/ }) {
        }
    }
}