package com.example.littlelemon

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val  sharedPref by lazy { this.getSharedPreferences("LittleLemon", MODE_PRIVATE) }
        val editor = sharedPref.edit()
        super.onCreate(savedInstanceState)
        setContent {
            val navcon = rememberNavController()
            NavHost(navController = navcon, startDestination = (if(sharedPref.getInt("loginCheck",0)==0) OnBoard.r else Home.r)){
                composable(Home.r){
                    Home(sharedPref,navcon)
                }
                composable(OnBoard.r)
                {
                    Onboarding(editor,navcon)
                }
                composable(Profile.r){
                    Profile(navcon,sharedPref,editor)
                }}

        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
    }