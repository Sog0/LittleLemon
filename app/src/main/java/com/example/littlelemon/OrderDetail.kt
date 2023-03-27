package com.example.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun OrderDetails(
    navcon: NavHostController,
    id: Long,
    orderViewModel: OrderViewModel,
    orderList: List<Orderd>
) {
    val order = orderList.firstOrNull(){it.id == id}!!
    Column(Modifier.fillMaxSize()) {

        Text(text = "${order.id}")
    }
}