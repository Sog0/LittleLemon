package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.example.littlelemon.ui.theme.karla


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishDetails(
    navcon: NavHostController,
    id: Int,
    databaseMenu: List<MenuItem>,
    sharedPref: SharedPreferences,
    orderList: List<Orderd>,
    orderViewModel: OrderViewModel
){
    var counter by remember {
        mutableStateOf(0)
    }


    var dish = databaseMenu.firstOrNull{it.id == id}



    Column(Modifier.fillMaxSize()) {
        
//        HomeHeader(navcon = navcon)
        
        Box(Modifier.fillMaxWidth()){
            GlideImage(
                model = dish?.image,
                contentDescription = dish?.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.FillHeight
            )

            FloatingActionButton(
                onClick = { navcon.navigate(Home.r)}
                , backgroundColor = LittleLemonColor.green
                , modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .size(40.dp)

            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = LittleLemonColor.cloud)
            }
    }

        Text(
            text = dish!!.title ,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.charcoal,
            fontFamily =  karla,
            fontSize = 40.sp,
            modifier =  Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
        )

        Text(
            text = dish.desc,
            color = LittleLemonColor.charcoal,
            fontFamily =  karla,
            fontSize = 20.sp,
            modifier =  Modifier.padding(vertical = 7.dp, horizontal = 10.dp)
        )

        Text(
            text = "$${dish.price}",
            color = LittleLemonColor.charcoal,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = karla,
            modifier =  Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {

            Button(
                onClick = { if(counter == 0) counter=0 else  counter-- },
                colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow)
                , shape = RoundedCornerShape(100)
            ) {
                Text(
                    text = "-" ,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(text = "$counter" ,
                color = LittleLemonColor.charcoal,
                modifier = Modifier.padding(top=5.dp),
                fontSize = 25.sp
            )

            Button(
                onClick = { counter++ },
                colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = "+",
                    fontSize = 20.sp
                )

            }

        }

        Button(
            onClick = {
                if(counter != 0){
                var tempOrderBody : MutableMap<String, Int> =  mutableMapOf(Pair(dish.title,counter))

                var uncomplete = orderList.firstOrNull(){it.checkReady == Ready.NOT_READY}

                if(orderViewModel.isEmpty() || uncomplete == null){

                    var ordering = Orderd(orderBody = tempOrderBody, customerEmail = sharedPref.getString("email",""))

                    orderViewModel.add(ordering)
                    }

                else{

                   uncomplete.orderBody!!.put(dish.title,counter + (if(uncomplete.orderBody.get(dish.title) == null) 0 else uncomplete.orderBody.get(dish.title)!!))

                    orderViewModel.add(uncomplete)

                }
                }
                navcon.navigate(Home.r)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.yellow),
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=20.dp, start = 30.dp, end= 30.dp)
        ) {
            Text(
                text = "Add for $${if(counter==0) dish.price else dish.price.toInt()*counter}",
                textAlign = TextAlign.Center,
                color = LittleLemonColor.charcoal,
                fontSize = 18.sp,
                fontFamily = karla,
                modifier = Modifier.padding(vertical = 0.dp)
            )
        }

}}








