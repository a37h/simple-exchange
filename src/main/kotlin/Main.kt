data class Order(val orderId: String, val side: Char, val price: Int, val quantity: Int)

fun parseOrder(s: String): Order {
    val order = s.split(",")

    val orderId:String=order[0]
    val side:Char=order[1].first()
    val price:Int=order[2].toInt()
    val quantity:Int=order[3].toInt()

    return Order(orderId, side, price, quantity)
}

fun main() {
    var inputString = readlnOrNull()

    while (inputString != null) {
        var order = parseOrder(inputString)
        println(order)
        inputString = readlnOrNull()
    }
}
