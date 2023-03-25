package com.example.littlelemon

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    val HttpClient = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text", "plain"))
        }

    }
    val database by lazy {
        Room.databaseBuilder(applicationContext,AppDatabase::class.java,"database").build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        val  sharedPref by lazy { this.getSharedPreferences("LittleLemon", MODE_PRIVATE) }
        val editor = sharedPref.edit()

        super.onCreate(savedInstanceState)
        setContent {
            val databaseMenu by database.menuItemDao().getAll().observeAsState(emptyList())
            val navcon = rememberNavController()
            NavHost(navController = navcon, startDestination = (if(sharedPref.getInt("loginCheck",0)==0) OnBoard.r else Home.r)){
                composable(Home.r){
                    Home(navcon,databaseMenu)
                }
                composable(OnBoard.r)
                {
                    Onboarding(editor,navcon)
                }
                composable(Profile.r){
                    Profile(navcon,sharedPref,editor)
                }}

        }
        lifecycleScope.launch(IO){
            if(database.menuItemDao().isEmpty()){
                val menuItemsNetwork = fetchMenu()
                saveMenu(menuItemsNetwork)

            }


        }

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        }
    suspend fun fetchMenu(): List<MenuItemNetwork>{
        return HttpClient.get ( "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json" ).body<MenuNetwork>().menu
    }
    fun saveMenu(menuitem :  List<MenuItemNetwork>){
        val menuItemRoom = menuitem.map{it.MenuToBase()}
        database.menuItemDao().add(*menuItemRoom.toTypedArray())
    }
    }