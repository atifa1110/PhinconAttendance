package com.example.phinconattendance.screen.main.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.phinconattendance.ui.theme.*
import com.example.phinconattendance.R
import com.example.phinconattendance.component.ButtonComponent
import com.example.phinconattendance.component.CommonDialog
import com.example.phinconattendance.screen.main.home.ContainerTitle
import com.example.phinconattendance.screen.main.home.HomeAppBar

@Composable
fun ProfileRoute(
    onNavigateToLogin : () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiState = uiState,
        onLogout = {
            viewModel.onLogout()
            viewModel.saveOnLoginState(false)
            onNavigateToLogin()
        },
    )
}

@Composable
fun ProfileScreen (
    uiState: ProfileUiState,
    onLogout: () -> Unit
) {
    if(uiState.isLoading) CommonDialog()

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

        Scaffold (
            topBar = {
                HomeAppBar(text = R.string.profile, icon = R.drawable.ic_edit)
            },
            backgroundColor = Color.Transparent
        ){
            ProfileContent(
                uiState = uiState,
                onLogout = onLogout,
                modifier = Modifier.padding(it)
            )
        }

    }
}

@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    onLogout : () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
            shape = RoundedCornerShape(8.dp), elevation = 5.dp
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 5.dp
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(painter = painterResource(id = R.drawable.img_1), contentDescription = "Photo Profile")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = uiState.name.toString(),
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W800,
                        fontStyle = FontStyle.Normal
                    ), color = TitleColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uiState.role.toString(),
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Normal
                    ), color = SkipColor
                )
            }
        }

        ContainerTitle(titleResourceId = R.string.general)

        CardGeneral(title = R.string.employee_number, subtitle = uiState.employeeNumber.toString() ,
            icon = R.drawable.karyawan )

        CardGeneral(title = R.string.address, subtitle = uiState.address.toString() ,
            icon = R.drawable.karyawan )

        CardGeneral(title = R.string.change_password, subtitle = "**********",
            icon = R.drawable.karyawan )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonComponent(
            value = R.string.logout,
            background = Color.Red,
            content = Color.White,
            onButtonClicked = onLogout,
            isEnabled = true
        )
    }
}

@Composable
fun CardGeneral(
    @StringRes title:Int,
    subtitle:String,
    @DrawableRes icon:Int
){
    Card(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp
    ) {

        Row(modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 5.dp
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(TabBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "image")
                }
            }
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = stringResource(id = title),
                    fontWeight = FontWeight.W800,
                    color = TitleColor,
                    fontSize = 16.sp
                )
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    PhinconAttendanceTheme {
        Surface {
            ProfileScreen(
                uiState =
                ProfileUiState(
                    name = " Atifa Fiorenza",
                    address = "GDC SEKTOR ANGGREK 3",
                    password = "*******",
                    role = "PROGRAMMER",
                    employeeNumber = "12345678"
                ),
                onLogout = {}
            )
        }
    }
}