package org.kollektions.intervalmap.range

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MinusWithInfinityTest {
    val lessThan1 = lessThan(1)
    val noGreaterThan1 = noGreaterThan(1)
    val noLessThan0 = noLessThan(0)
    val greaterThan0 = greaterThan(0)

    @Test
    fun nothingLeftFromMinusInfinity() {
        assertAll(
                { assertTrue { (lessThan1 - lessThan(2)).isEmpty() } },
                { assertTrue { (lessThan1 - lessThan1).isEmpty() } },
                { assertTrue { (lessThan1 - noGreaterThan1).isEmpty() } },
                { assertTrue { (noGreaterThan1 - lessThan(2)).isEmpty() } },
                { assertTrue { (noGreaterThan1 - noGreaterThan1).isEmpty() } },
                { assertTrue { (noGreaterThan1 - allValues()).isEmpty() } }
        )
    }

    @Test
    fun nothingLeftFromPlusInfinity() {
        assertAll(
                { assertTrue { (noLessThan0 - noLessThan(-1)).isEmpty() } },
                { assertTrue { (noLessThan0 - noLessThan0).isEmpty() } },
                { assertTrue { (greaterThan0 - noLessThan0).isEmpty() } },
                { assertTrue { (greaterThan0 - greaterThan0).isEmpty() } },
                { assertTrue { (greaterThan0 - allValues()).isEmpty() } }
        )
    }

    @Test
    fun oneItemLeftFromMinusInfinity() {
        assertAll(
                { assertEquals(listOf(lessThan(0)), lessThan1 - noLessThan(0)) },
                { assertEquals(listOf(noGreaterThan(0)), lessThan1 - greaterThan(0)) },
                { assertEquals(listOf(lessThan(0)), lessThan1 - (0 closedClosed 2)) },
                { assertEquals(listOf(noGreaterThan(0)), lessThan1 - (0 openClosed 2)) }
        )
    }

    @Test
    fun oneItemLeftFromPlusInfinity() {
        assertAll(
                { assertEquals(listOf(lessThan(0)), lessThan1 - noLessThan(0)) },
                { assertEquals(listOf(noGreaterThan(0)), lessThan1 - greaterThan(0)) },
                { assertEquals(listOf(lessThan(0)), lessThan1 - (0 closedClosed 2)) },
                { assertEquals(listOf(noGreaterThan(0)), lessThan1 - (0 openClosed 2)) }
        )
    }

    @Test
    fun twoItemsLeftFromMinusInfinity() {
        assertAll(
                { assertEquals(listOf(lessThan(-2), -1 openOpen 1), lessThan1 - (-2 closedClosed -1)) },
                { assertEquals(listOf(noGreaterThan(-2), -1 openOpen 1), lessThan1 - (-2 openClosed -1)) }
        )
    }

    @Test
    fun twoItemsLeftFromPlusInfinity() {
        assertAll(
                { assertEquals(listOf(0 closedOpen 1, greaterThan(2)), noLessThan0 - (1 closedClosed 2)) },
                { assertEquals(listOf(0 closedClosed 1, greaterThan(2)), noLessThan0 - (1 openClosed 2)) }
        )
    }
}