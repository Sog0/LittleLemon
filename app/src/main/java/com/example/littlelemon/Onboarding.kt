package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.karla


@Composable
fun Onboarding(editor: SharedPreferences.Editor,navcon : NavHostController) {
    val context = LocalContext.current
    var openDialog by remember {
        mutableStateOf(false)
    }
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
    Header()
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
        Button(onClick = { if(userName != "" && userSurname != "" && userEmail != "")
        {
            editor.putString("name", userName).apply();
            editor.putString("surname", userSurname).apply();
            editor.putString("email", userEmail).apply();
            editor.putInt("loginCheck",1).apply();
            navcon.navigate(Home.r)
        }
        else{
         openDialog= true   
        }}
            , modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 80.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
            shape = RoundedCornerShape(9.dp) 
        ) { Text(text = "Register" , textAlign = TextAlign.Center, fontSize = 20.sp, fontFamily = karla)}
        if (openDialog){
            AlertDialog(
                onDismissRequest = {openDialog=false},
                title = { Text(text = "Incorrect input")},
                text = { Text(text = "Please, fill up all fields")},
                confirmButton = { Button(onClick = { openDialog =false }) {
                    Text(text = "Confirm")
                }}
            )
        }
        
    }    
    }
}


@Composable
fun Header(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(Color(0xFFFFFFFF)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            Modifier
                .height(70.dp)
                .padding(10.dp)
        )
    }
}
