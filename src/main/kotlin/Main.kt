import java.util.TreeSet

data class Order(val orderId: String, val side: Char, val price: Int, val quantity: Int)

fun parseOrder(s: String): Order {
    val order = s.split(",")

    val orderId:String=order[0]
    val side:Char=order[1].first()
    val price:Int=order[2].toInt()
    val quantity:Int=order[3].toInt()

    return Order(orderId, side, price, quantity)
}

data class Limit(val arrivalIndex: Int, val orderId: String, val price: Int, val quantity: Int)

fun processOrder(bids: TreeSet<Limit>, asks: TreeSet<Limit>, arrivalIndex: Int, o: Order) {
    println("#: $arrivalIndex order: $o")
    if (o.side == 'B') {
        bids.add(Limit(arrivalIndex, o.orderId, o.price, o.quantity))
    }
    if (o.side == 'S') {
        asks.add(Limit(arrivalIndex, o.orderId, o.price, o.quantity))
    }
}

fun main() {
    // arrivalIndex - represents order arrival chronology for matching orders in price time priority
    var arrivalIndex = 0

    val bids = TreeSet(compareByDescending<Limit> { it.price }.thenBy { it.arrivalIndex })
    val asks = TreeSet(compareBy<Limit> { it.price }.thenBy { it.arrivalIndex })

    var inputString = readlnOrNull()

    while (inputString != null) {
        val order = parseOrder(inputString)
        arrivalIndex += 1
        processOrder(bids, asks, arrivalIndex, order)
        inputString = readlnOrNull()
    }

    println("Bids: $bids")
    println("Asks: $asks")
}
