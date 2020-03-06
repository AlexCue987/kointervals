package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MutableRangeMapRemoveRangeTest {
    @Test
    fun doesNothingForEmpty() {
        val sut = mutableRangeMapOf<Int, String>()
        val hoursRange = 11 openOpen 15
        sut.remove(hoursRange)
        assertTrue(sut.isEmpty())
    }

    @Test
    fun doesNothingBeforeExisting() {
        val sunny = "Sunny"
        val sunnyHours = 11 openOpen 15
        for(beforeExisting in listOf(9 closedOpen 11, lessThan(11))) {
            val sut = mutableRangeMapOf(sunnyHours to sunny)
            sut.remove(beforeExisting)
            assertEquals(listOf(sunnyHours to sunny).toRangeMapEntryList(), sut.toList())
        }
    }

    @Test
    fun doesNothingAfterExisting() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        for(afterExisting in listOf(11 openOpen 15, greaterThan(11))) {
            val sut = mutableRangeMapOf(cloudyHours to cloudy)
            sut.remove(afterExisting)
            assertEquals(listOf(cloudyHours to cloudy).toRangeMapEntryList(), sut.toList())
        }
    }

    @Test
    fun doesNothingBetweenExisting() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunnyHours = 11 openOpen 15
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut.remove(sunnyHours)
        assertEquals(listOf(cloudyHours to cloudy, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesStartOfExistingRangeWithOpenEnd() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunnyHours = 15 closedOpen 16
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut.remove(sunnyHours)
        assertEquals(listOf(cloudyHours to cloudy, 16 closedOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesStartOfExistingRangeWithClosedEnd() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunnyHours = 15 closedClosed 16
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut.remove(sunnyHours)
        assertEquals(listOf(cloudyHours to cloudy, 16 openOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesEndOfExistingRangeWithOpenStart() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunnyHours = 10 openOpen 12
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut.remove(sunnyHours)
        assertEquals(listOf(9 closedClosed 10 to cloudy, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesEndOfExistingRangeWithClosedStart() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunnyHours = 10 closedOpen 12
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut.remove(sunnyHours)
        assertEquals(listOf(9 closedOpen 10 to cloudy, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesMiddleOfExistingRange() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunnyHours = 16 closedOpen 17
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut.remove(sunnyHours)
        assertEquals(listOf(cloudyHours to cloudy, 15 closedOpen 16 to windy, 17 closedOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun removesLastExistingRange() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        for(coversLastRange in listOf(windyHours, noLessThan(15))) {
            val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
            sut.remove(coversLastRange)
            assertEquals(listOf(cloudyHours to cloudy).toRangeMapEntryList(), sut.toList())
        }
    }

    @Test
    fun removesFirstExistingRange() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        for(coversLastRange in listOf(cloudyHours, lessThan(11))) {
            val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
            sut.remove(coversLastRange)
            assertEquals(listOf(windyHours to windy).toRangeMapEntryList(), sut.toList())
        }
    }

    @Test
    fun removesAllExistingRanges() {
        val rangesThatCoverAllSut = listOf(9 closedOpen 18,
                lessThan(18), noLessThan(9), allValues())
        for (range in rangesThatCoverAllSut) {
            val cloudy = "Cloudy"
            val cloudyHours = 9 closedOpen 11
            val windy = "Windy"
            val windyHours = 15 closedOpen 18
            val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
            sut.remove(range)
            val description = "Removing $range"
            assertTrue(sut.isEmpty(), description)
        }
    }
}