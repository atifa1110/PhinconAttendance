package com.example.phinconattendance.screen.main.history

import androidx.annotation.StringRes
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.phinconattendance.R
import com.example.phinconattendance.component.AttendanceListItem
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.fetchAttendanceResponseList
import com.example.phinconattendance.ui.theme.Background
import com.example.phinconattendance.ui.theme.TabBackground
import com.example.phinconattendance.ui.theme.TextTab
import com.example.phinconattendance.ui.theme.TextTabSelected

@Composable
fun TabContent(
    items : List<AttendanceResponse>,
    selectedTab: HistoryFilterType,
    @StringRes noTasksLabel: Int,
    onSetFilterType: (HistoryFilterType) -> Unit,
) {
    // List of tabs from the enum
    val history = HistoryFilterType.values()

    Column(modifier = Modifier.fillMaxSize()) {
        // Display a TabRow with scrollable tabs
        TabRow(selectedTabIndex = history.indexOf(selectedTab),
            backgroundColor = Background,
            contentColor = Color.Transparent,
            modifier = Modifier
                .background(Color.Transparent)
                .clip(RoundedCornerShape(10.dp)),
        ) {
            val color = remember {
                Animatable(TabBackground)
            }
            history.forEach { tab ->
                Tab(
                    modifier = Modifier.background(
                        color = if(selectedTab == tab) color.value else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    selected = selectedTab == tab,
                    onClick = { onSetFilterType(tab) },
                    text = {
                        Text(
                            text = tab.title,
                            fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp,
                            color = if(selectedTab== tab) TextTabSelected else TextTab
                        )
                    }
                )
            }
        }

        when(selectedTab){
            HistoryFilterType.DAY -> {
                HistoryContent(
                    empty = items.isEmpty(),
                    emptyContent = { HistoryEmptyContent(noTasksLabel = noTasksLabel) },
                    items = items
                )
            }
            HistoryFilterType.WEEK -> {
                HistoryContent(
                    empty = items.isEmpty(),
                    emptyContent = { HistoryEmptyContent(noTasksLabel = noTasksLabel) },
                    items = items
                )
            }
            HistoryFilterType.MONTH ->  {
                HistoryContent(
                    empty = items.isEmpty(),
                    emptyContent = { HistoryEmptyContent(noTasksLabel = noTasksLabel) },
                    items = items
                )
            }
            HistoryFilterType.YEAR -> {
                HistoryContent(
                    empty = items.isEmpty(),
                    emptyContent = { HistoryEmptyContent(noTasksLabel = noTasksLabel) },
                    items = items
                )
            }
        }
    }
}

@Composable
private fun HistoryContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    items : List<AttendanceResponse>,
) {
    if (empty) {
        emptyContent()
    } else {
        TabListContent(items = items)
    }
}

@Composable
private fun HistoryEmptyContent(
    @StringRes noTasksLabel: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = noTasksLabel),
            color = TextTab,
            fontSize = 12.sp
        )
    }
}

@Composable
fun TabListContent(items : List<AttendanceResponse>){
    LazyColumn(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ){
        items(items){
            AttendanceListItem(attendance = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabScreenPreview() {
    MaterialTheme {
        TabContent(
            items = emptyList(),
            selectedTab = HistoryFilterType.DAY,
            noTasksLabel = R.string.no_history_day,
            onSetFilterType = {}
        )
    }
}
