package domain

data class Order(val orderId: String, val side: Char, val price: Int, var quantity: Int) {
    override fun toString(): String {
        return "domain.Order(side=$side, price=$price, quantity=$quantity)"
    }
}

fun parseOrder(s: String): Order {
    val order = s.split(",")

    val orderId:String=order[0]
    val side:Char=order[1].first()
    val price:Int=order[2].toInt()
    val quantity:Int=order[3].toInt()

    return Order(orderId, side, price, quantity)
}
