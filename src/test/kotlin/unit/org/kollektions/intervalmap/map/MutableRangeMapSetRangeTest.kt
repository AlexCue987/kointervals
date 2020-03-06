package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.closedClosed
import org.kollektions.intervalmap.closedOpen
import org.kollektions.intervalmap.openClosed
import org.kollektions.intervalmap.openOpen
import org.junit.Test
import kotlin.test.assertEquals

class MutableRangeMapSetRangeTest {
    @Test
    fun addsToEmpty() {
        val sut = mutableRangeMapOf<Int, String>()
        val sunny = "Sunny"
        val hoursRange = 11 openOpen 15
        sut[hoursRange] = sunny
        assertEquals(listOf(hoursRange to sunny).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun addsBeforeExisting() {
        val sunny = "Sunny"
        val sunnyHours = 11 openOpen 15
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sut = mutableRangeMapOf(sunnyHours to sunny)
        sut[cloudyHours] = cloudy
        assertEquals(listOf(cloudyHours to cloudy, sunnyHours to sunny).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun addsAfterExisting() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 11 openOpen 15
        val sut = mutableRangeMapOf(cloudyHours to cloudy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(cloudyHours to cloudy, sunnyHours to sunny).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun addsBetweenExisting() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 11 openOpen 15
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(cloudyHours to cloudy, sunnyHours to sunny, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesStartOfExistingRangeWithOpenEnd() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 15 closedOpen 16
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(cloudyHours to cloudy, sunnyHours to sunny, 16 closedOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesStartOfExistingRangeWithClosedEnd() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 15 closedClosed 16
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(cloudyHours to cloudy, sunnyHours to sunny, 16 openOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesEndOfExistingRangeWithOpenStart() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 10 openOpen 12
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(9 closedClosed 10 to cloudy, sunnyHours to sunny, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesEndOfExistingRangeWithClosedStart() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 10 closedOpen 12
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(9 closedOpen 10 to cloudy, sunnyHours to sunny, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesMiddleOfExistingRange() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val sunny = "Sunny"
        val sunnyHours = 16 closedOpen 17
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[sunnyHours] = sunny
        assertEquals(listOf(cloudyHours to cloudy, 15 closedOpen 16 to windy, sunnyHours to sunny, 17 closedOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesExistingRange() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sunny = "Sunny"
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[windyHours] = sunny
        assertEquals(listOf(cloudyHours to cloudy, windyHours to sunny).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun replacesAllExistingRanges() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sunny = "Sunny"
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        val sunnyHours = 9 closedOpen 18
        sut[sunnyHours] = sunny
        assertEquals(listOf(sunnyHours to sunny).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun mergesWhenSameValueAtStartOverlaps() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[10 closedClosed 12] = cloudy
        assertEquals(listOf(9 closedClosed 12 to cloudy, windyHours to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun mergesWhenSameValueAtEndOverlaps() {
        val cloudy = "Cloudy"
        val cloudyHours = 9 closedOpen 11
        val windy = "Windy"
        val windyHours = 15 closedOpen 18
        val sut = mutableRangeMapOf(cloudyHours to cloudy, windyHours to windy)
        sut[14 closedClosed 16] = windy
        assertEquals(listOf(cloudyHours to cloudy, 14 closedOpen 18 to windy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun mergesWhenSameValuesAtBothEndsOverlap() {
        val cloudy = "Cloudy"
        val sut = mutableRangeMapOf(9 closedOpen 11 to cloudy,
                15 closedOpen 18 to cloudy)
        sut[10 closedClosed 16] = cloudy
        assertEquals(listOf(9 closedOpen 18 to cloudy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun mergesWhenSameValuesAtBothEndsAdjacent() {
        val cloudy = "Cloudy"
        val sut = mutableRangeMapOf(9 closedOpen 11 to cloudy,
                15 openOpen 18 to cloudy)
        sut[11 closedClosed 15] = cloudy
        assertEquals(listOf(9 closedOpen 18 to cloudy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun mergesWhenSameValuesAtBothEndsAdjacentAtStart() {
        val cloudy = "Cloudy"
        val sut = mutableRangeMapOf(9 closedOpen 11 to cloudy,
                15 openOpen 18 to cloudy)
        sut[11 closedOpen 15] = cloudy
        assertEquals(listOf(9 closedOpen 15 to cloudy, 15 openOpen 18 to cloudy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun mergesWhenSameValuesAtBothEndsAdjacentAtEnd() {
        val cloudy = "Cloudy"
        val sut = mutableRangeMapOf(9 closedOpen 11 to cloudy,
                15 openOpen 18 to cloudy)
        sut[11 openClosed  15] = cloudy
        assertEquals(listOf(9 closedOpen 11 to cloudy, 11 openOpen 18 to cloudy).toRangeMapEntryList(), sut.toList())
    }

    @Test
    fun doesNotMergeWhenSameValuesAtBothEndsNotAdjacent() {
        val cloudy = "Cloudy"
        val morning = 9 closedOpen 11
        val afternoon = 15 openOpen 18
        val sut = mutableRangeMapOf(morning to cloudy,
                afternoon to cloudy)
        val longLunch = 11 openOpen 15
        sut[longLunch] = cloudy
        assertEquals(listOf(morning to cloudy, longLunch to cloudy, afternoon to cloudy).toRangeMapEntryList(), sut.toList())
    }
}