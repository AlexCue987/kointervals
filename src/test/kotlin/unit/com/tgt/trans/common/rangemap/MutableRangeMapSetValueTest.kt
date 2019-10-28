package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.closedClosed
import com.tgt.trans.common.range.closedOpen
import com.tgt.trans.common.range.openClosed
import com.tgt.trans.common.range.openOpen
import org.junit.Test
import kotlin.test.assertEquals

class MutableRangeMapSetValueTest {
    @Test
    fun setsValueForSinglePointInside() {
        val sut = mutableRangeMapOf(9 closedClosed 12 to "Cloudy")
        sut[10] = "Rain"
        assertEquals(listOf(9 closedOpen 10 to "Cloudy", 10 closedClosed 10 to "Rain", 10 openClosed 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun setDoesNothingIfValueSame() {
        val cloudy = "Cloudy"
        val sut = mutableRangeMapOf(9 closedClosed 12 to cloudy)
        sut[9] = cloudy
        sut[10] = cloudy
        sut[11] = cloudy
        sut[12] = cloudy
        assertEquals(listOf(9 closedClosed 12 to cloudy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun setsValueForSinglePointOnOpenStart() {
        val sut = mutableRangeMapOf(9 openClosed 12 to "Cloudy")
        sut[9] = "Rain"
        assertEquals(listOf(9 closedClosed 9 to "Rain", 9 openClosed 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun setsValueForSinglePointOnClosedStart() {
        val sut = mutableRangeMapOf(9 closedClosed 12 to "Cloudy")
        sut[9] = "Rain"
        assertEquals(listOf(9 closedClosed 9 to "Rain", 9 openClosed 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun setsValueForSinglePointOnOpenEnd() {
        val sut = mutableRangeMapOf(9 openOpen 12 to "Cloudy")
        sut[12] = "Rain"
        assertEquals(listOf(9 openOpen 12 to "Cloudy", 12 closedClosed 12 to "Rain").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun setsValueForSinglePointOnClosedEnd() {
        val sut = mutableRangeMapOf(9 openClosed 12 to "Cloudy")
        sut[12] = "Rain"
        assertEquals(listOf(9 openOpen 12 to "Cloudy", 12 closedClosed 12 to "Rain").toRangeMapEntryList(), sut.toList())
    }
}