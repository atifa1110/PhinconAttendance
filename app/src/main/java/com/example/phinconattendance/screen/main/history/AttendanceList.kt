package com.example.phinconattendance.screen.main.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phinconattendance.ui.theme.SubTitleColor
import com.example.phinconattendance.ui.theme.TitleColor
import com.example.phinconattendance.R
import com.example.phinconattendance.data.model.AttendanceResponse
import com.example.phinconattendance.data.model.PlaceResponse
import com.example.phinconattendance.data.model.Time

@Composable
fun AttendanceListItem(attendance: AttendanceResponse){

    Column(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(8.dp), elevation = 5.dp
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_1),
                                contentDescription = "image"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    ) {
                        Text(
                            text = attendance.item?.attend +" - "+attendance.item?.company+" - "+Time().convertToTime(attendance.item?.time),
                            //text = "Check In - PT. Phincon - 9:00 AM",
                            fontWeight = FontWeight.W800,
                            color = TitleColor,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = attendance.item!!.address,
                            //text = "Office. 88 @Kasablanka Office Tower 18th Floor",
                            fontWeight = FontWeight.W400,
                            color = SubTitleColor,
                            fontSize = 11.sp
                        )
                    }
                }
        }
    }
}