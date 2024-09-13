package com.example.phinconattendance.screen.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phinconattendance.R
import com.example.phinconattendance.data.model.Place
import com.example.phinconattendance.data.model.PlaceResponse
import com.example.phinconattendance.data.model.fetchPlaceList
import com.example.phinconattendance.ui.theme.Blue
import com.example.phinconattendance.ui.theme.PhinconAttendanceTheme
import com.example.phinconattendance.ui.theme.SubTitleColor
import com.example.phinconattendance.ui.theme.TitleColor

@Composable
fun LocationCard(
    place: Place,
    onItemClick: (Place) -> Unit,
    isSelected : Boolean,
    isClickable : Boolean
){
    Card(modifier = Modifier.fillMaxWidth()
        .clickable(enabled = isClickable) { onItemClick(place) },
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp,
        backgroundColor = if (isSelected) Blue else Color.White
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(5.dp), elevation = 2.dp
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = place.company,
                    fontWeight = FontWeight.W800,
                    color = if (isSelected) Color.White else TitleColor,
                    fontSize = 12.sp
                )

                Text(
                    text = place.address,
                    fontWeight = FontWeight.W400,
                    color = if (isSelected) Color.White else SubTitleColor,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview(){
    PhinconAttendanceTheme {
        LocationCard(
            place = fetchPlaceList()[0],
            onItemClick = {},
            isSelected  = false,
            isClickable = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardClickPreview(){
    PhinconAttendanceTheme {
        LocationCard(
            place = fetchPlaceList()[0],
            onItemClick = { /*TODO*/ },
            isSelected = true,
            isClickable  = false
        )
    }
}