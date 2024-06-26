package domain

data class Limit(val arrivalIndex: Int, val orderId: String, val price: Int, var quantity: Int, var side: Char) {
    companion object {
        const val QUANTITY_FORMAT_LENGTH = 11
        const val PRICE_FORMAT_LENGTH = 6
        const val EMPTY_PADDING_LENGTH = 18
    }

    fun formatQuantity(q: Int): String {
        val number = String.format("%,d", q)
        val padding = " ".repeat(QUANTITY_FORMAT_LENGTH -number.length)
        return padding + number
    }

    fun formatPrice(p: Int): String {
        val number = p.toString()
        val padding = " ".repeat(PRICE_FORMAT_LENGTH -number.length)
        return padding + number
    }

    override fun toString(): String {
        return if (side == 'B') {
            "${formatQuantity(quantity)} ${formatPrice(price)}"
        } else {
            "${formatPrice(price)} ${formatQuantity(quantity)}"
        }
    }
}
