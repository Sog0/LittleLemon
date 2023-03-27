package com.example.littlelemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.example.littlelemon.ui.theme.karla

@Composable
fun OrderList(
    navcon: NavHostController,
    orderList: List<Orderd>,
    orderViewModel: OrderViewModel,
    databaseMenu: List<MenuItem>
){
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn() {
                items(orderList) { order ->
                    Order(order, orderViewModel,databaseMenu,navcon)
                }
            }
        }

        FloatingActionButton(
            onClick = { navcon.navigate(Home.r) },
            backgroundColor = LittleLemonColor.green,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 20.dp)
                .size(60.dp)
                .align(Alignment.BottomStart)

        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = LittleLemonColor.cloud,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}


@Composable
fun Order(
    order: Orderd,
    orderViewModel: OrderViewModel,
    databaseMenu: List<MenuItem>,
    navcon: NavHostController
) {
    var openDialog by remember{ mutableStateOf(false)}
    Card(
        shape = RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp, topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .clickable{ navcon.navigate(OrderDet.r + "/${order.id}") }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Order id number #${order.id}",
                    fontSize = 18.sp,
                    fontFamily = karla,
                    color = LittleLemonColor.charcoal,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(top = 14.dp)
                )

                IconButton(onClick = {openDialog= true}) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Delete")
                }
            }

            val pxStart = with(LocalDensity.current) { 280.dp.toPx() }
            val pxEnd = with(LocalDensity.current) { 250.dp.toPx() }

            Row(Modifier.fillMaxWidth())
            {
                Column(Modifier.fillMaxWidth()) {

                    var filterByOrder by remember {
                        mutableStateOf(dishFilter(databaseMenu, order))
                    }

                    LazyRow(
                        Modifier
                            .width(280.dp)
                            .graphicsLayer { alpha = 0.97F }
                            .drawWithContent {
                                val colors = listOf(Color.Transparent, Color.Black)
                                drawContent()
                                drawRect(
                                    brush = Brush.horizontalGradient(
                                        colors = colors,
                                        startX = pxStart,
                                        endX = pxEnd
                                    ),
                                    blendMode = BlendMode.DstIn
                                )
                            }
                            .padding(start = 5.dp, top = 15.dp)
                    ) {
                        items(filterByOrder) {
                            dishMenuature(it)
                        }
                    }
                    Text(
                        text = "Total amount: $${countAmount(order.orderBody, filterByOrder)}",
                        fontSize = 14.sp,
                        fontFamily = karla,
                        color = LittleLemonColor.charcoal,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(top = 10.dp, start = 30.dp)
                    )

                }
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "arrow",
                    modifier = Modifier
                        .height(40.dp)
                        .width(20.dp)
                        .padding(end = 20.dp)
                    ,
                )

            }
        }

    }




    if (openDialog){
        AlertDialog(
            onDismissRequest = {
                openDialog=false
            },
            text = { Text(text = "Are you sure you want to delete this order?") },
            confirmButton = {
                Button(onClick = {
                    orderViewModel.remove(order)
                    openDialog =false
                })
                {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                openDialog =false
            })
            {
                Text(text = "No")
            }
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun dishMenuature(menuItem: MenuItem) {
    Column(modifier = Modifier
        .padding(start = 15.dp)
        .width(50.dp)) {
        GlideImage(
            model = menuItem.image,
            contentDescription = menuItem.title,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,

        )
        Text(
            text = menuItem.title,
            fontFamily = karla,
            color= LittleLemonColor.charcoal,
            fontSize = 9.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp),
            textAlign = TextAlign.Center
            )
    }
}






fun dishFilter(databaseMenu: List<MenuItem>, order: Orderd) : MutableList<MenuItem>{
    var filteredList : MutableList<MenuItem> = mutableListOf()
    order.orderBody.forEach{ check ->
        filteredList += databaseMenu.filter {
            it.title== check.key
        }
    }
    return filteredList
}


fun countAmount(values: MutableMap<String, Int>, filterByOrder: MutableList<MenuItem>) : Int {
    var amount : Int = 0
    values.forEach{map ->
        amount += map.value * (filterByOrder.firstOrNull(){ it.title == map.key}!!.price.toInt())
    }
    return amount
}