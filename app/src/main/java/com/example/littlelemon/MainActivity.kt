package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import androidx.room.Room
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val  sharedPref by lazy { this.getSharedPreferences("LittleLemon", MODE_PRIVATE) }
        val editor = sharedPref.edit()

        super.onCreate(savedInstanceState)
        setContent {
            Column() {
             var checkWhichScreen by remember {
                 mutableStateOf(0)
             }
            val databaseMenu by database.menuItemDao().getAll().observeAsState(emptyList())
            val navcon = rememberAnimatedNavController()
            if(checkWhichScreen == 0){Scaffold(topBar = { HomeHeader(navcon = navcon) }, modifier = Modifier
                .fillMaxWidth()
                .height(70.dp) ){}}
                AnimatedNavHost(navController = navcon, startDestination = (if(sharedPref.getInt("loginCheck",0)==0) OnBoard.r else Home.r)) {

                composable(Home.r,
                    enterTransition = { fadeIn(animationSpec = tween(700))},
                    exitTransition = { fadeOut(animationSpec = tween(400))},
                    popEnterTransition = {fadeIn(animationSpec = tween(400))}
                ) {
                    Home(navcon, databaseMenu)
                    checkWhichScreen = 0
                }

                composable(OnBoard.r)
                {
                    Onboarding(editor, navcon)
                    checkWhichScreen = 1
                }

                composable(
                    Profile.r,
                    popExitTransition = { fadeOut(animationSpec = tween(400))},
                    enterTransition = {fadeIn(animationSpec = tween(400))}
                ) {
                    Profile(navcon, sharedPref, editor)
                    checkWhichScreen = 1
                }

                composable(
                    Dish.r + "/{${Dish.dishId}}",
                    enterTransition = { slideInVertically(initialOffsetY = {1500}, animationSpec =spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMediumLow)) },
                    popExitTransition = { slideOutVertically(targetOffsetY = {2000}) },
                    exitTransition = { slideOutVertically(targetOffsetY = {2000}, animationSpec = tween(500)) },
                    arguments = listOf(navArgument(Dish.dishId) { type = NavType.IntType },
                        )
                ) {
                    val id = requireNotNull(it.arguments?.getInt(Dish.dishId))
                    DishDetails(navcon, id, databaseMenu)
                    checkWhichScreen = 0
            }}
        }}




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
                .padding(top = 10.dp, bottom = 10.dp, start = 100.dp)
        )
        IconButton(onClick = {navcon.navigate(Profile.r)} , Modifier.padding(top= 13.dp, bottom = 10.dp, start = 55.dp) ) {

            Image(painter = painterResource(id = R.drawable.profile) , contentDescription = "Default profile photo" , Modifier.size(40.dp) )
        }

    }
}
