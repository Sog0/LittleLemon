package com.example.littlelemon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonColor

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
    val order = orderList.firstOrNull(){it.id == id}!!
    val orderBody = order.orderBody
    val menuList = fromMaptoOrderlist(orderBody)
    LazyColumn(){
        items(menuList){pos ->
            OrderEditorPlate(count = pos.count, menuItem = MenuList.firstOrNull{it.title == pos.title}!!,orderViewModel,order )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrderEditorPlate(
    count: Int,
    menuItem: MenuItem,
    orderViewModel: OrderViewModel,
    order: Orderd
){
    var temp_counter by remember {
        mutableStateOf(count)
    }
    var openDialog by remember {
        mutableStateOf(false)
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
                IconButton(onClick = {
                    if(temp_counter>1){
                        temp_counter = remove_pos(
                            order = order,
                            orderViewModel = orderViewModel,
                            count = temp_counter,
                            title = menuItem.title
                        )
                    }
                    else{
                        openDialog = true
                    }
                                     },
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
            IconButton(onClick = {
                temp_counter = add_pos(
                    order = order,
                    orderViewModel = orderViewModel,
                    count =temp_counter,
                    title = menuItem.title
                )
                                 },
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
    //02:13 28.07.23 STOPPED HERE
    if(openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            text = { Text(text = "Are you sure you want to delete this ${menuItem.title} from order?") },
            confirmButton = {
                Button(onClick = {
                    order.orderBody.remove(menuItem.title)
                    orderViewModel.add(order)
                    openDialog = false
                })
                {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog = false
                })
                {
                    Text(text = "No")
                }
            }
        )
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


fun remove_pos(orderViewModel: OrderViewModel, title: String, count : Int, order: Orderd): Int{
    var count = count-1
    order.orderBody[title] = count
    orderViewModel.add(order)
    return count
}

fun add_pos(orderViewModel: OrderViewModel,title: String, count : Int, order: Orderd): Int{
    var count = count+1
    order.orderBody[title] = count
    orderViewModel.add(order)
    return count
}