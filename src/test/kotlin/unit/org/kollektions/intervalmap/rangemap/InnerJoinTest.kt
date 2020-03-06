package org.kollektions.intervalmap.rangemap

import org.kollektions.intervalmap.range.closedClosed
import org.kollektions.intervalmap.range.closedOpen
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InnerJoinTest {
    val emptyMap = rangeMapOf<Int, String>()

    val weather = rangeMapOf(9 closedClosed 11 to "Windy", 11 closedClosed 15 to "Rainy")

    val tasks = rangeMapOf(8 closedClosed 13 to "TPS Report",
            13 closedClosed 14 to "Lunch",
            14 closedClosed 17 to "Cover letter for TPS report")

    @Test
    fun twoEmptyMaps() {
        val actual = innerJoin(emptyMap, emptyMap)
        assertTrue(actual.isEmpty())
    }

    @Test
    fun firstMapEmpty() {
        val actual = innerJoin(emptyMap, weather)
        assertTrue(actual.isEmpty())
    }

    @Test
    fun secondMapEmpty() {
        val actual = innerJoin(weather, emptyMap)
        assertTrue(actual.isEmpty())
    }

    @Test
    fun joins() {
        val actual = innerJoin(weather, tasks)
        val expected = rangeMapOf(
                9 closedOpen 11 to Pair("Windy", "TPS Report"),
                11 closedOpen 13 to Pair("Rainy", "TPS Report"),
                13 closedOpen 14 to Pair("Rainy", "Lunch"),
                14 closedClosed 15 to Pair("Rainy", "Cover letter for TPS report"))
        assertEquals(expected.toList(), actual.toList())
    }
}