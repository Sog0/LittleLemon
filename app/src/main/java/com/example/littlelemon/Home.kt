package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Home(sharedPref: SharedPreferences, navcon: NavHostController) {
Column() {
    Text(text = "Hello ${sharedPref.getString("name",null)} ${sharedPref.getString("surname",null)}", fontSize = 100.sp)
    Button(onClick = {navcon.navigate(Profile.r)}) {

    }
}
}