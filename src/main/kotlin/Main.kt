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
    companion object {
        const val QUANTITY_FORMAT_LENGTH = 11
        const val PRICE_FORMAT_LENGTH = 6
    }

    private fun formatQuantity(q: Int): String {
        val number = String.format("%,d", q)
        val padding = " ".repeat(QUANTITY_FORMAT_LENGTH-number.length)
        return padding + number
    }

    private fun formatPrice(p: Int): String {
        val number = p.toString()
        val padding = " ".repeat(PRICE_FORMAT_LENGTH-number.length)
        return padding + number
    }

    override fun toString(): String {
        return "${formatPrice(price)} ${formatQuantity(quantity)}"
    }
}

class OrderBook {

    val bids = TreeSet(compareByDescending<Limit> { it.price }.thenBy { it.arrivalIndex })
    val asks = TreeSet(compareBy<Limit> { it.price }.thenBy { it.arrivalIndex })

    // arrivalIndex - represents order arrival chronology for matching orders in price time priority
    var arrivalIndex = 0

    fun processOrder(o: Order) {
        arrivalIndex += 1
        if (o.side == 'B') {
            processBuyOrder(o)
        } else {
            processSellOrder(o)
        }
    }

    // Buy order is first considered for aggressive matching against the Asks.
    // Once this is completed, any remaining order quantity will rest on the Bids side of the book.
    private fun processBuyOrder(currentOrder: Order) {
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
    private fun processSellOrder(o: Order) {}

    override fun toString(): String {
        var result = "---------------------------------\n"

        val iteratorBids = bids.iterator()
        val iteratorAsks = asks.iterator()

        while (iteratorBids.hasNext() && iteratorAsks.hasNext()) {
            val bid = iteratorBids.next()
            val ask = iteratorAsks.next()
            result += "$bid | $ask\n"
        }
        while (iteratorBids.hasNext()) {
            val bid = iteratorBids.next()
            result += "$bid |\n"
        }
        while (iteratorAsks.hasNext()) {
            val ask = iteratorAsks.next()
            result += "${" ".repeat(18)} | $ask\n"
        }

        result += "---------------------------------\n"
        return result
    }
}

fun main() {
    val orderBook = OrderBook()

    var inputString = readlnOrNull()

    while (inputString != null) {
        val order = parseOrder(inputString)
        orderBook.processOrder(order)
        inputString = readlnOrNull()
    }

    println(orderBook)
}
