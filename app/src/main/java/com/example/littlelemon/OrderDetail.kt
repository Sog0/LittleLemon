package com.example.littlelemon

import android.graphics.drawable.Icon
import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonColor
import java.security.Key

@Composable
fun OrderDetails(
    navcon: NavHostController,
    id: Long,
    orderViewModel: OrderViewModel,
    orderList: List<Orderd>,
    MenuList: List<MenuItem>
) {
    OrderEditor(orderList = orderList, orderViewModel = orderViewModel, MenuList = MenuList, id = id )
}



@Composable
fun OrderEditor(
    orderList: List<Orderd>,
    orderViewModel: OrderViewModel,
    id: Long,
    MenuList: List<MenuItem>
){
    val order = orderList.firstOrNull(){it.id == id}!!.orderBody
    val menuList = fromMaptoOrderlist(order)
    LazyColumn(){
        items(menuList){pos ->
            OrderEditorPlate(count = pos.count, menuItem = MenuList.firstOrNull{it.title == pos.title}!!)
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrderEditorPlate(
    count: Int,
    menuItem: MenuItem
){
    var temp_counter by remember {
        mutableStateOf(count)
    } 
    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
        GlideImage(model = menuItem.image,
            contentDescription = menuItem.title,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .size(90.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            Modifier.width(width = 200.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            //MINUS COUNTER BUTTON
                IconButton(onClick = { /*TODO*/ },
                    Modifier
                        .clip(shape = RoundedCornerShape(100))
                        .background(color = LittleLemonColor.charcoal)
                        .size(30.dp)
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = "remove",
                        tint = LittleLemonColor.cloud
                    )
                }

                //COUNTER TEXT
                Text(text = "$temp_counter" ,
                    color = LittleLemonColor.charcoal,
                    modifier = Modifier.padding(top=5.dp),
                    fontSize = 25.sp
                )

                //PLUS COUNTER BUTTON
            IconButton(onClick = { /*TODO*/ },
                Modifier
                    .clip(shape = RoundedCornerShape(100))
                    .background(color = LittleLemonColor.charcoal)
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "remove",
                    tint = LittleLemonColor.cloud,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

            }
        }
    }
}




data class OrderedDish(
    val title : String,
    val count : Int
)


fun fromMaptoOrderlist(data : MutableMap<String,Int>): List<OrderedDish>{
    var list = mutableListOf<OrderedDish>()
    data.forEach{
        list.add(OrderedDish(it.key,it.value))
    }
    return list
}


//17:55 27.07.23 STOPPED HERE
fun remove_pos(orderViewModel: OrderViewModel, id: Long,title: String, count : Int): Int{

    return count
}

fun add_pos(){

}