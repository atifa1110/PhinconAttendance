package com.example.phinconattendance.screen.main.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.phinconattendance.R
import com.example.phinconattendance.component.*
import com.example.phinconattendance.data.model.Place
import com.example.phinconattendance.data.model.fetchPlaceList
import com.example.phinconattendance.ui.theme.*

@Composable
fun HomeRoute(
    viewModel :HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onCheckIn = viewModel::onCheckInButtonClick,
        onLocationSelected = viewModel::onLocationSelected
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCheckIn: () -> Unit,
    onLocationSelected: (Place) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MainColor),
            contentAlignment = Alignment.TopEnd
        ) {
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
                HomeAppBar(text = R.string.your_attendance, icon = R.drawable.ic_notification)
            },
            backgroundColor = Color.Transparent
        ){
            HomeContent(
                uiState = uiState,
                onCheckInClick = onCheckIn,
                onLocationSelected = onLocationSelected,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onCheckInClick : () -> Unit,
    onLocationSelected: (Place) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ){
        item {
            Spacer(modifier = Modifier.height(16.dp))
            CheckInCard(
                currentTime = uiState.currentTime,
                currentDate = uiState.currentDate,
                isButtonEnable = uiState.isButtonEnable,
                isButtonClick = uiState.isButtonCheckIn,
                onCheckIn = onCheckInClick
            )
        }

        item{
            ContainerTitle(titleResourceId = R.string.location)
        }

        items(fetchPlaceList()) {
            LocationCard(
                place = it,
                onItemClick = { place -> onLocationSelected(place) },
                isSelected = uiState.selectedLocation.company == it.company,
                isClickable = !uiState.isButtonCheckIn
            )
        }
    }
}

@Composable
fun ContainerTitle(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleResourceId),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            color = TitleColor
        )
    }
}

@Composable
fun HomeAppBar(
    @StringRes text : Int,
    @DrawableRes icon:Int
){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = text),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.W800,
                fontStyle = FontStyle.Normal
            ), color = Color.White
        )
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Notification"
            )
        }
    }
}


@Composable
fun CheckInCard(
    currentTime : String,
    currentDate : String,
    isButtonEnable: Boolean,
    isButtonClick : Boolean,
    onCheckIn : () -> Unit
){
    val buttonColor = if(isButtonClick) ButtonDefaults.buttonColors(Yellow) else ButtonDefaults.buttonColors(Green)
    val buttonText = if(isButtonClick) stringResource(R.string.check_out) else stringResource(R.string.check_in)

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp),
        shape = RoundedCornerShape(8.dp), elevation = 5.dp
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TextComponent(value = "Hour: $currentTime")
                TextComponent(value = currentDate)
            }

            Button(
                modifier = Modifier.size(195.dp),
                onClick = { onCheckIn() },
                shape = CircleShape,
                colors = buttonColor,
                enabled = isButtonEnable
            ) {
                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 30.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W700
                    )
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PhinconAttendanceTheme {
        Surface {
            HomeScreen(
                uiState = HomeUiState(
                    currentTime = "17:30",
                    currentDate = "07 September 2024",
                    isButtonEnable = true,
                    isButtonCheckIn = true,
                    selectedLocation = fetchPlaceList()[0],
                ),
                onCheckIn = {},
                onLocationSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckInCardPreview(){
    PhinconAttendanceTheme {
        Surface {
            CheckInCard(
                currentTime = "17:30",
                currentDate = "07 September 2024",
                isButtonEnable = true,
                isButtonClick = false,
                onCheckIn = {}
            )
        }
    }
}