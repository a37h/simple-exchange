import domain.Order
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class OrderTest {

    @Test
    fun testParseOrder() {
        val orderString = "10002,S,100,10000"
        val order = Order.parseOrder(orderString)

        assertEquals("10002", order.orderId)
        assertEquals('S', order.side)
        assertEquals(100, order.price)
        assertEquals(10000, order.quantity)
    }

    @Test
    fun testParseOrder_InvalidString() {
        assertFailsWith<IllegalArgumentException> {
            Order.parseOrder("B,100,10,11")
        }
        assertFailsWith<IllegalArgumentException> {
            Order.parseOrder("10002S,100,10000")
        }
    }

    @Test
    fun testConstructor_InvalidOrderId() {
        assertFailsWith<IllegalArgumentException> {
            Order("", 'B', 100, 10)
        }
    }

    @Test
    fun testConstructor_InvalidSide() {
        assertFailsWith<IllegalArgumentException> {
            Order("orderId", 'A', 100, 10)
        }
    }

    @Test
    fun testConstructor_InvalidPrice() {
        assertFailsWith<IllegalArgumentException> {
            Order("orderId", 'B', 0, 10)
        }
        assertFailsWith<IllegalArgumentException> {
            Order("orderId", 'B', 1000000, 10)
        }
    }

    @Test
    fun testConstructor_InvalidQuantity() {
        assertFailsWith<IllegalArgumentException> {
            Order("orderId", 'B', 100, 0)
        }
        assertFailsWith<IllegalArgumentException> {
            Order("orderId", 'B', 100, 1000000000)
        }
    }
}
