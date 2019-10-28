package com.tgt.trans.common.examples

import com.tgt.trans.common.range.closedClosed
import com.tgt.trans.common.range.closedOpen
import com.tgt.trans.common.range.openClosed
import com.tgt.trans.common.rangemap.fullOuterJoin
import com.tgt.trans.common.rangemap.rangeMapOf
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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