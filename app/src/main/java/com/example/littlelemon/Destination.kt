package com.example.littlelemon

interface Destination{
    var r:String
}

object Home: Destination{
    override var r = "Home"

}
object OnBoard: Destination{
    override var r = "OnBoard"
}
object Profile: Destination{
    override var r = "Profile"
}

object Dish: Destination{
    override var r = "Dish"
    const val dishId = "dishId"
}

object OrderList: Destination{
    override var r = "OrderList"
}

object OrderDet : Destination{
    override var r = "OrderDet"
    const val orderId = "orderId"
}