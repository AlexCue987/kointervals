package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MutableRangeMapRemoveValueTest {
    @Test
    fun removeDoesNothing() {
        for(range in listOf(1 closedClosed 5, lessThan(5), greaterThan(7))) {
            val sut = mutableRangeMapOf(range to "Rainy")
            val outsideAllRanges = 6
            assertFalse("Guardian assumption: value outside the range") { range.contains(outsideAllRanges) }
            sut.remove(outsideAllRanges)
            assertEquals(listOf(range to "Rainy").toRangeMapEntryList(), sut.toList())
        }
    }

    @Test
    fun removesSinglePointInsideRange() {
        val sut = mutableRangeMapOf(9 closedClosed 12 to "Cloudy")
        sut.remove(10)
        assertEquals(listOf(9 closedOpen 10 to "Cloudy", 10 openClosed 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesSinglePointInsideGreaterThan() {
        val sut = mutableRangeMapOf(greaterThan(1) to "Cloudy")
        sut.remove(10)
        assertEquals(listOf(1 openOpen 10 to "Cloudy", greaterThan(10) to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesSinglePointInsideLessThan() {
        val sut = mutableRangeMapOf(lessThan(20) to "Cloudy")
        sut.remove(10)
        assertEquals(listOf(lessThan(10) to "Cloudy", 10 openOpen 20 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesSinglePointInsideAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Cloudy")
        sut.remove(10)
        assertEquals(listOf(lessThan(10) to "Cloudy", greaterThan(10) to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun doesNothingForSinglePointOnOpenStart() {
        val sut = mutableRangeMapOf(9 openClosed 12 to "Cloudy")
        sut.remove(9)
        assertEquals(listOf(9 openClosed 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesSinglePointOnClosedStart() {
        val sut = mutableRangeMapOf(9 closedClosed 12 to "Cloudy")
        sut.remove(9)
        assertEquals(listOf(9 openClosed 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun doesNothingForSinglePointOnOpenEnd() {
        val sut = mutableRangeMapOf(9 openOpen 12 to "Cloudy")
        sut.remove(12)
        assertEquals(listOf(9 openOpen 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesSinglePointOnClosedEnd() {
        val sut = mutableRangeMapOf(9 openClosed 12 to "Cloudy")
        sut.remove(12)
        assertEquals(listOf(9 openOpen 12 to "Cloudy").toRangeMapEntryList(), sut.toList())
    }
}