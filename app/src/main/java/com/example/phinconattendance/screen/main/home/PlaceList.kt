package com.example.phinconattendance.screen.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.phinconattendance.data.model.PlaceResponse
import com.example.phinconattendance.ui.theme.Blue
import com.example.phinconattendance.ui.theme.SubTitleColor
import com.example.phinconattendance.ui.theme.TitleColor

@Composable
fun ClickListItem(
    place: PlaceResponse.Place = PlaceResponse.Place(
        "PT. Phincon",
        "Office. 88 @Kasablanka Office Tower 18th Floor"
    ),
    onItemClick: () -> Unit,
    isClicked : Boolean,
    modifier: Modifier = Modifier){

    //var isClicked by remember {mutableStateOf(false) }

    val backgroundColor = if (isClicked) Blue else Color.White

    Column(modifier.padding(top = 10.dp, bottom = 10.dp)) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },shape = RoundedCornerShape(8.dp), elevation = 2.dp) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)) {
                Row(modifier = Modifier.padding(10.dp),
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
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    ) {
                        Text(
                            text = place.company,
                            fontWeight = FontWeight.W800,
                            color = if (isClicked) Color.White else TitleColor,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = place.address,
                            fontWeight = FontWeight.W400,
                            color = if (isClicked) Color.White else SubTitleColor,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview(){

}