import domain.Order
import domain.OrderBook

fun main() {
    val orderBook = OrderBook()

    var inputString = readlnOrNull()

    while (inputString != null) {
        val order = Order.parseOrder(inputString)
        orderBook.routeOrder(order)
        inputString = readlnOrNull()
    }

    print(orderBook)
}
