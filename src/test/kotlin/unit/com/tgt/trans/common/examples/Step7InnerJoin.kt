package com.tgt.trans.common.examples

import com.tgt.trans.common.range.closedClosed
import com.tgt.trans.common.range.closedOpen
import com.tgt.trans.common.rangemap.innerJoin
import com.tgt.trans.common.rangemap.rangeMapOf
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Step7InnerJoin {
    val weather = rangeMapOf(9 closedClosed 11 to "Windy", 11 closedClosed 15 to "Rainy")

    val tasks = rangeMapOf(8 closedClosed 13 to "TPS Report",
            13 closedClosed 14 to "Lunch")

    @Test
    fun joins() {
        val actual = innerJoin(weather, tasks)
        val expected = rangeMapOf(
                9 closedOpen 11 to Pair("Windy", "TPS Report"),
                11 closedOpen 13 to Pair("Rainy", "TPS Report"),
                13 closedClosed 14 to Pair("Rainy", "Lunch"))
        assertEquals(expected.toList(), actual.toList())
    }
}