import java.util.TreeSet
import kotlin.math.min

data class Order(val orderId: String, val side: Char, val price: Int, var quantity: Int) {
    override fun toString(): String {
        return "Order(side=$side, price=$price, quantity=$quantity)"
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

data class Limit(val arrivalIndex: Int, val orderId: String, val price: Int, var quantity: Int) {
    override fun toString(): String {
        return "Limit(price=$price, quantity=$quantity)"
    }
}


// Buy order is first considered for aggressive matching against the Asks.
// Once this is completed, any remaining order quantity will rest on the Bids side of the book.
fun processBuyOrder(bids: TreeSet<Limit>, asks: TreeSet<Limit>, arrivalIndex: Int, currentOrder: Order) {
    //      100       1,000   <--- new buy order
    //
    // Selling for:
    // |    100         500
    // |    100      10,000
    // |    103         100
    // |    105      20,000

    if (asks.size == 0) {
        bids.add(Limit(arrivalIndex, currentOrder.orderId, currentOrder.price, currentOrder.quantity))
        return
    }

    while (asks.size > 0) {
        val bestOffer = asks.first() // 500 @ 100

        if (bestOffer.price <= currentOrder.price) {
            val matchedQuantity = min(bestOffer.quantity, currentOrder.quantity) // min(500, 1000) = 500
            bestOffer.quantity -= matchedQuantity
            currentOrder.quantity -= matchedQuantity

            // should we remove bestOffer?
            if (bestOffer.quantity == 0) {
                asks.remove(bestOffer)
            }

            // We should stop if we've filled current order completely
            if (currentOrder.quantity == 0) {
                return
            }

            // We should stop if Asks is empty
            if (asks.size == 0) {
                bids.add(Limit(arrivalIndex, currentOrder.orderId, currentOrder.price, currentOrder.quantity))
                return
            }

            // We can continue aggressive matching
        } else {
            bids.add(Limit(arrivalIndex, currentOrder.orderId, currentOrder.price, currentOrder.quantity))
            return
        }
    }
}

// Sell order is first considered for aggressive matching against the Bids.
// Once this is completed, any remaining order quantity will rest on the Asks side of the book.
fun processSellOrder(bids: TreeSet<Limit>, asks: TreeSet<Limit>, arrivalIndex: Int, currentOrder: Order) {

}

fun processOrder(bids: TreeSet<Limit>, asks: TreeSet<Limit>, arrivalIndex: Int, o: Order) {
    if (o.side == 'B') {
        processBuyOrder(bids, asks, arrivalIndex, o)
    } else {
        processSellOrder(bids, asks, arrivalIndex, o)
    }

//    if (o.side == 'B') {
//        bids.add(Limit(arrivalIndex, o.orderId, o.price, o.quantity))
//    }
//    if (o.side == 'S') {
//        asks.add(Limit(arrivalIndex, o.orderId, o.price, o.quantity))
//    }
}

fun main() {
    // arrivalIndex - represents order arrival chronology for matching orders in price time priority
    var arrivalIndex = 0

    val bids = TreeSet(compareByDescending<Limit> { it.price }.thenBy { it.arrivalIndex })
    val asks = TreeSet(compareBy<Limit> { it.price }.thenBy { it.arrivalIndex })

    // |    100         500
    // |    100      10,000
    // |    103         100
    // |    105      20,000
    asks.add(Limit(1, "id1", 100, 500))
    asks.add(Limit(2, "id2", 100, 10000))
    asks.add(Limit(3, "id3", 103, 100))
    asks.add(Limit(4, "id4", 105, 20000))

    //      100       1,000   <--- new buy order
    var currentOrder = Order("id5", 'B', 100, 1000)

    println("bids: $bids")
    println("asks: $asks")

    processBuyOrder(bids, asks, 5, currentOrder)

    println("bids: $bids")
    println("asks: $asks")
//    var inputString = readlnOrNull()
//
//    while (inputString != null) {
//        val order = parseOrder(inputString)
//        arrivalIndex += 1
//        processOrder(bids, asks, arrivalIndex, order)
//        inputString = readlnOrNull()
//    }

}
