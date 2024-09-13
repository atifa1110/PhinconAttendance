package com.example.phinconattendance.screen.main.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.phinconattendance.ui.theme.CircleColor
import com.example.phinconattendance.ui.theme.MainColor
import com.example.phinconattendance.R
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.fetchAttendanceResponseList
import com.example.phinconattendance.screen.main.home.ContainerTitle
import com.example.phinconattendance.screen.main.home.HomeAppBar
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryRoute(
    viewModel: HistoryViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedFilter.collectAsStateWithLifecycle()

    HistoryScreen(
        selectedTab = selectedTab,
        items = uiState.item,
        noTasksLabel =  uiState.filterUiState.noTasksLabel,
        onSetFilterType = viewModel::setFiltering
    )
}

@Composable
fun HistoryScreen(
    selectedTab : HistoryFilterType,
    items: List<AttendanceResponse>,
    @StringRes noTasksLabel: Int,
    onSetFilterType: (HistoryFilterType) -> Unit,
){
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
                HomeAppBar(text = R.string.attendance_history, icon = R.drawable.ic_notification)
            },
            backgroundColor = Color.Transparent
        ){
            HistoryContent(
                selectedTab = selectedTab,
                items = items,
                noTasksLabel = noTasksLabel,
                onSetFilterType = onSetFilterType,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
fun HistoryContent(
    selectedTab : HistoryFilterType,
    items: List<AttendanceResponse>,
    @StringRes noTasksLabel: Int,
    onSetFilterType: (HistoryFilterType) -> Unit,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        shape = RoundedCornerShape(8.dp), elevation = 5.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ContainerTitle(titleResourceId = R.string.logs)
            TabContent(
                items = items,
                selectedTab = selectedTab,
                noTasksLabel = noTasksLabel,
                onSetFilterType = onSetFilterType
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview(){
    PhinconAttendanceTheme {
        Surface {
            HistoryScreen(
                selectedTab = HistoryFilterType.DAY,
                items = fetchAttendanceResponseList(),
                noTasksLabel = R.string.no_history_day,
                onSetFilterType = {}
            )
        }
    }
}