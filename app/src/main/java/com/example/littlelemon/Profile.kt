package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Profile(
    navcon: NavHostController,
    sharedPref: SharedPreferences,
    editor: SharedPreferences.Editor
) {
Button(onClick = {editor.putInt("loginCheck",0).apply(); navcon.navigate(OnBoard.r)}) {
    
}
}