package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.closedClosed
import com.tgt.trans.common.range.closedOpen
import com.tgt.trans.common.range.openClosed
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FullOuterJoinTest {
    val emptyMap = rangeMapOf<Int, String>()

    val rainyPeriod = 11 closedClosed 18
    val weather = rangeMapOf(rainyPeriod to "Rainy")

    val tpsReportPeriod = 10 closedClosed 13
    val tasks = rangeMapOf(tpsReportPeriod to "TPS Report")

    @Test
    fun twoEmptyMaps() {
        val actual = fullOuterJoin(emptyMap, emptyMap)
        assertTrue(actual.isEmpty())
    }

    @Test
    fun firstMapEmpty() {
        val actual = fullOuterJoin(emptyMap, weather)
        val expected = rangeMapOf(
                rainyPeriod to Pair(Optional.empty<String>(), Optional.of("Rainy")))
        assertEquals(expected.toList(), actual.toList())
    }

    @Test
    fun secondMapEmpty() {
        val actual = fullOuterJoin(weather, emptyMap)
        val expected = rangeMapOf(
                rainyPeriod to Pair(Optional.of("Rainy"), Optional.empty<String>()))
        assertEquals(expected.toList(), actual.toList())
    }

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