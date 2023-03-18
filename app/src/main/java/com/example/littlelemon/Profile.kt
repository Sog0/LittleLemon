package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.karla

@Composable
fun Profile(
    navcon: NavHostController,
    sharedPref: SharedPreferences,
    editor: SharedPreferences.Editor
) {
    Column {
    Header()
        Row(
            Modifier
                .background(color = Color(0xFF495E57))
                .fillMaxWidth()
                .height(90.dp),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your profile",
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = karla
            )
        }
        Row(Modifier.fillMaxWidth()) {
            Image(painter = painterResource(id = R.drawable.profile), contentDescription = "Profile photo",
                Modifier
                    .size(200.dp)
                    .padding(30.dp)
                    .border(
                        width = 3.dp,
                        color = Color(0xFFF4CE14),
                        shape = RoundedCornerShape(70.dp)
                    )

            )
            Column() {
                InformationZone(sharedPref)
            }

        }
        Column(Modifier.fillMaxWidth().padding(start = 30.dp)) {
            Text(text = "Email" , fontSize = 12.sp)
            Text(text = "${sharedPref.getString("email",null)}", fontSize = 20.sp, fontFamily = karla, modifier = Modifier.padding(top=3.dp))
        }
    ButtonLogOutAndAlert(editor,navcon)
    }
}
 @Composable
fun ButtonLogOutAndAlert(editor: SharedPreferences.Editor, navcon: NavHostController) {
     var openDialog by remember {
         mutableStateOf(false)
     }
     Button(onClick = {openDialog =true}
         , modifier = Modifier
             .fillMaxWidth()
             .padding(start = 14.dp, end = 14.dp, top = 50.dp)
             .height(50.dp),
         colors = ButtonDefaults.buttonColors(Color(0xFFF4CE14)),
         shape = RoundedCornerShape(9.dp)
     ) { Text(text = "Logout" , textAlign = TextAlign.Center, fontSize = 20.sp, fontFamily = karla) }
     if (openDialog){
            AlertDialog(
             onDismissRequest = {
                 openDialog=false
                                },
             text = { Text(text = "Are you sure you want log out?") },
             confirmButton = {
                 Button(onClick = {
                    openDialog =false
                    editor.putInt("loginCheck", 0).apply()
                     navcon.navigate(OnBoard.r)
                })
             {
                 Text(text = "Yes")
             }
             },
             dismissButton = {Button(onClick = {
                 openDialog =false
             })
             {
                 Text(text = "No")
             }}
         )
     }
}


@Composable
fun InformationZone(sharedPref: SharedPreferences){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp), verticalArrangement = Arrangement.spacedBy(50.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "First name" , fontSize = 12.sp)
            Text(text = "${sharedPref.getString("name",null)}", fontSize = 20.sp, fontFamily = karla, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=3.dp))
        }
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Last name", fontSize = 12.sp)
            Text(text = "${sharedPref.getString("surname",null)}", fontSize = 20.sp, fontFamily = karla, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=3.dp))
        }
    }
}