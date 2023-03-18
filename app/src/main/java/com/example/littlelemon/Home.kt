package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun Home(sharedPref: SharedPreferences, navcon: NavHostController) {
Column(modifier = Modifier.fillMaxSize()) {
    HomeHeader(navcon)
    }
}



@Composable
fun HomeHeader(navcon: NavHostController){
    Row(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFFFFFFFF)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            Modifier
                .height(73.dp)
                .padding(top= 10.dp, bottom = 10.dp, start = 100.dp)
        )
        IconButton(onClick = {navcon.navigate(Profile.r)} , Modifier.padding(top= 13.dp, bottom = 10.dp, start = 55.dp) ) {
            Image(painter = painterResource(id = R.drawable.profile) , contentDescription = "Default profile photo" , Modifier.size(40.dp) )
        }
    }
}

