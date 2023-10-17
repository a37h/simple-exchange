package domain

data class Limit(val arrivalIndex: Int, val orderId: String, val price: Int, var quantity: Int) {
    companion object {
        const val QUANTITY_FORMAT_LENGTH = 11
        const val PRICE_FORMAT_LENGTH = 6
    }

    private fun formatQuantity(q: Int): String {
        val number = String.format("%,d", q)
        val padding = " ".repeat(QUANTITY_FORMAT_LENGTH -number.length)
        return padding + number
    }

    private fun formatPrice(p: Int): String {
        val number = p.toString()
        val padding = " ".repeat(PRICE_FORMAT_LENGTH -number.length)
        return padding + number
    }

    override fun toString(): String {
        return "${formatPrice(price)} ${formatQuantity(quantity)}"
    }
}
