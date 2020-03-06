package org.kollektions.intervalmap.examples

import org.kollektions.intervalmap.closedClosed
import org.kollektions.intervalmap.closedOpen
import org.kollektions.intervalmap.openClosed
import org.kollektions.intervalmap.map.outerJoin
import org.kollektions.intervalmap.map.rangeMapOf
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class Step8LeftOuterJoin {
    val weather = rangeMapOf(8 closedClosed 11 to "Windy", 11 closedClosed 18 to "Rainy")

    val tasks = rangeMapOf(10 closedClosed 13 to "TPS Report")
    @Test
    fun joins() {
        val actual = outerJoin(weather, tasks)
        val expected = rangeMapOf(
                8 closedOpen 10 to Pair("Windy", Optional.empty<String>()),
                10 closedOpen 11 to Pair("Windy", Optional.of("TPS Report")),
                11 closedClosed 13 to Pair("Rainy", Optional.of("TPS Report")),
                13 openClosed 18 to Pair("Rainy", Optional.empty<String>())
                )
        assertEquals(expected.toList(), actual.toList())
    }
}