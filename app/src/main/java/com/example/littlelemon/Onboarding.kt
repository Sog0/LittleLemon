package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.karla
import com.example.littlelemon.ui.theme.markazi


@Composable
fun Onboarding(){
    var userName by remember {
        mutableStateOf("")
    }
    var userSurname by remember {
        mutableStateOf("")
    }
    var userEmail by remember {
        mutableStateOf("")
    }


    Column(Modifier.fillMaxSize()) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            Modifier
                .height(70.dp)
                .padding(10.dp)
        )
    }
    Row(
        Modifier
            .background(color = Color(0xFF495E57))
            .fillMaxWidth()
            .height(125.dp),
        horizontalArrangement = Arrangement.Center, verticalAlignment = CenterVertically
    ) {
        Text(
            text = "Let's get to know you",
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = karla
        )
    }
    Text(
        text = "Personal information",
        fontFamily = karla,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 30.dp, start = 15.dp, bottom = 30.dp),
        fontSize = 20.sp
        )
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "First name" ,
                color= Color.Black,
                modifier = Modifier.padding(start = 21.dp, bottom = 2.dp),
                fontSize = 15.sp
            )
            OutlinedTextField(
                value = userName,
                onValueChange = {item -> userName= item},
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                singleLine = true
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)) {
            Text(
                text = "Last name" ,
                color= Color.Black,
                modifier = Modifier.padding(start = 21.dp, bottom = 2.dp),
                fontSize = 15.sp
            )
            OutlinedTextField(
                value = userSurname,
                onValueChange = {item -> userSurname= item},
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                singleLine = true

            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)) {
            Text(
                text = "Email" ,
                color= Color.Black,
                modifier = Modifier.padding(start = 21.dp, bottom = 2.dp),
                fontSize = 15.sp
            )
            OutlinedTextField(
                value = userEmail,
                onValueChange = {item -> userEmail= item},
                shape = RoundedCornerShape(9.dp),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                singleLine = true
            )
        }
        Button(onClick = { /*TODO*/ } , modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 14.dp, top = 80.dp)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
            shape = RoundedCornerShape(9.dp) 
        ) { Text(text = "Register" , textAlign = TextAlign.Center, fontSize = 20.sp, fontFamily = karla)}

        
    }    
    }
}


@Preview(showBackground = true)
@Composable
fun Prew(){
    Onboarding()
}


