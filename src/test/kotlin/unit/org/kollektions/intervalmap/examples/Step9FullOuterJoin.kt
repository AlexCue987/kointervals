package org.kollektions.intervalmap.examples

import org.kollektions.intervalmap.closedClosed
import org.kollektions.intervalmap.closedOpen
import org.kollektions.intervalmap.openClosed
import org.kollektions.intervalmap.map.fullOuterJoin
import org.kollektions.intervalmap.map.rangeMapOf
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class Step9FullOuterJoin {
    val rainyPeriod = 11 closedClosed 18
    val weather = rangeMapOf(rainyPeriod to "Rainy")

    val tpsReportPeriod = 10 closedClosed 13
    val tasks = rangeMapOf(tpsReportPeriod to "TPS Report")

    @Test
    fun joins() {
        val actual = fullOuterJoin(tasks, weather)
        val expected = rangeMapOf(
                10 closedOpen 11 to Pair(Optional.of("TPS Report"), Optional.empty<String>()),
                11 closedClosed 13 to Pair(Optional.of("TPS Report"), Optional.of("Rainy")),
                13 openClosed 18 to Pair(Optional.empty<String>(),  Optional.of("Rainy"))
                )
        assertEquals(expected.toList(), actual.toList())
    }
}