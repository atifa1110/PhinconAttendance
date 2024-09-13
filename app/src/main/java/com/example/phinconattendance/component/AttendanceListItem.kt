package com.example.phinconattendance.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phinconattendance.ui.theme.SubTitleColor
import com.example.phinconattendance.ui.theme.TitleColor
import com.example.phinconattendance.R
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.Time
import com.example.phinconattendance.data.model.fetchAttendanceResponse
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme

@Composable
fun AttendanceListItem(
    attendance: AttendanceResponse
){
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(8.dp), elevation = 5.dp
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = "image"
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = attendance.item?.attend +" - "+attendance.item?.company+" - "+Time().convertToTime(attendance.item?.time),
                fontWeight = FontWeight.W800,
                color = TitleColor,
                fontSize = 12.sp
            )
            Text(
                text = attendance.item?.address?:"",
                fontWeight = FontWeight.W400,
                color = SubTitleColor,
                fontSize = 11.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AttendanceListPreview(){
    PhinconAttendanceTheme {
        AttendanceListItem(
            attendance = fetchAttendanceResponse()
        )
    }
}