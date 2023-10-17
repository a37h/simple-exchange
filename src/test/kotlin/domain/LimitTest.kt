package domain

import kotlin.test.Test
import kotlin.test.assertEquals

internal class LimitTest {

    @Test
    fun testFormatQuantity() {
        assertEquals("        100", Limit(1, "orderId", 100, 100, 'B').formatQuantity(100))
        assertEquals("     12,345", Limit(1, "orderId", 100, 12345, 'B').formatQuantity(12345))
        assertEquals("123,456,789", Limit(1, "orderId", 100, 123456789, 'B').formatQuantity(123456789))
    }

    @Test
    fun testFormatPrice() {
        assertEquals("    10", Limit(1, "orderId", 100, 100, 'B').formatPrice(10))
        assertEquals("   100", Limit(1, "orderId", 100, 100, 'B').formatPrice(100))
        assertEquals("  1000", Limit(1, "orderId", 100, 100, 'B').formatPrice(1000))
    }

    @Test
    fun testToString() {
        assertEquals("         10    100", Limit(1, "orderId", 100, 10, 'B').toString())
        assertEquals("      1,000    100", Limit(1, "orderId", 100, 1000, 'B').toString())
        assertEquals("     10,000    100", Limit(1, "orderId", 100, 10000, 'B').toString())

        assertEquals("   100          10", Limit(1, "orderId", 100, 10, 'S').toString())
        assertEquals("   100       1,000", Limit(1, "orderId", 100, 1000, 'S').toString())
        assertEquals("   100      10,000", Limit(1, "orderId", 100, 10000, 'S').toString())
    }
}
