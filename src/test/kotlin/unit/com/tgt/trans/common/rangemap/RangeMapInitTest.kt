package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.closedClosed
import org.junit.Test
import kotlin.test.assertFailsWith

class RangeMapInitTest {
    @Test
    fun cannotCreateIfOverlap() {
        assertFailsWith(IllegalArgumentException::class) {
            RangeMap(listOf(closedClosed(1..3) to "Yoda", closedClosed(2..4) to "R2D2").toRangeMapEntryList())
        }
    }

    @Test
    fun cannotCreateIfNotOrdered() {
        assertFailsWith(IllegalArgumentException::class) {
            RangeMap(listOf(closedClosed(10..30) to "Yoda", closedClosed(2..4) to "R2D2").toRangeMapEntryList())
        }
    }
}