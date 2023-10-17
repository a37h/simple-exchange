import domain.OrderBook
import domain.parseOrder

fun main() {
    val orderBook = OrderBook()

    var inputString = readlnOrNull()

    while (inputString != null) {
        val order = parseOrder(inputString)
        orderBook.routeOrder(order)
        inputString = readlnOrNull()
    }

    println(orderBook)
}
