package domain

class Order(
    val orderId: String,
    val side: Char,
    val price: Int,
    var quantity: Int
) {
    init {
        require(orderId.isNotEmpty()) { "orderId must not be empty" }
        require(side == 'B' || side == 'S') { "side must be either 'B' (buy) or 'S' (sell)" }
        require(price in 1..999999) { "price must be greater than 0" }
        require(quantity in 2..999999998) { "quantity must be greater than 0" }
    }

    companion object {
        fun parseOrder(s: String): Order {
            val order = s.split(",")

            require(order.size == 4) { "Invalid order string: $s" }

            val orderId = order[0]
            val side = order[1].first()
            val price = order[2].toInt()
            val quantity = order[3].toInt()

            return Order(orderId, side, price, quantity)
        }
    }

    override fun toString(): String {
        return "domain.Order(side=$side, price=$price, quantity=$quantity)"
    }
}
