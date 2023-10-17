package domain

import java.util.TreeSet
import kotlin.math.min

class OrderBook {

    private val bids = TreeSet(compareByDescending<Limit> { it.price }.thenBy { it.arrivalIndex })
    private val asks = TreeSet(compareBy<Limit> { it.price }.thenBy { it.arrivalIndex })

    // arrivalIndex - represents order arrival chronology for matching orders in price time priority
    private var arrivalIndex = 0

    fun routeOrder(o: Order) {
        arrivalIndex += 1
        if (o.side == 'B') {
            processOrder(o, asks, bids) { a, b -> a <= b }
        } else {
            processOrder(o, bids, asks) { a, b -> a >= b }
        }
    }

    // Buy order is first considered for aggressive matching against the Asks.
    // Once this is completed, any remaining order quantity will rest on the Bids side of the book.
    private fun processOrder(currentOrder: Order, matchingSide: TreeSet<Limit>, restingSide: TreeSet<Limit>, priceCondition: (Int, Int) -> Boolean) {
        if (matchingSide.size == 0) {
            restingSide.add(Limit(arrivalIndex, currentOrder.orderId, currentOrder.price, currentOrder.quantity))
            return
        }

        while (matchingSide.size > 0) {
            val bestOffer = matchingSide.first()

            if (priceCondition(bestOffer.price, currentOrder.price)) {
                val matchedQuantity = min(bestOffer.quantity, currentOrder.quantity) // min(500, 1000) = 500
                bestOffer.quantity -= matchedQuantity
                currentOrder.quantity -= matchedQuantity

                // Remove bestOffer if it was filled
                if (bestOffer.quantity == 0) {
                    matchingSide.remove(bestOffer)
                }

                // Stop if the currentOrder was filled
                if (currentOrder.quantity == 0) {
                    return
                }

                // Stop if Asks is empty
                if (matchingSide.size == 0) {
                    restingSide.add(Limit(arrivalIndex, currentOrder.orderId, currentOrder.price, currentOrder.quantity))
                    return
                }

                // Continue aggressive matching
            } else {
                // Couldn't find a matching price in Asks, resting the currentOrder
                restingSide.add(Limit(arrivalIndex, currentOrder.orderId, currentOrder.price, currentOrder.quantity))
                return
            }
        }
    }

    override fun toString(): String {
        var result = ""
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
            result += "${" ".repeat(Limit.EMPTY_PADDING_LENGTH)} | $ask\n"
        }

        return result
    }
}
