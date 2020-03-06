package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.*
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class RangeMapGetTest {
    @Test
    fun get_returnsNothingIfRangeBeforeMap(){
        val firstRangeInMap = closedClosed(1..3)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", closedClosed(4..5) to "R2D2")
        val range = openOpen(0..1)
        Assert.assertTrue("Guardian assumption", range.isBefore(firstRangeInMap))
        val actual = sut[range]
        Assert.assertTrue(actual.isEmpty())
    }

    @Test
    fun get_returnsNothingIfRangeAfterMap(){
        val firstRangeInMap = closedClosed(1..3)
        val lastRangeInMap = 4 closedOpen 5
        val sut = rangeMapOf(firstRangeInMap to "Yoda", lastRangeInMap to "R2D2")
        val range = closedOpen(5..6)
        Assert.assertTrue("Guardian assumption", lastRangeInMap.isBefore(range))
        val actual = sut[range]
        Assert.assertTrue(actual.isEmpty())
    }

    @Test
    fun get_returnsOneRangeOverlapped() {
        val firstRangeInMap = closedClosed(1..3)
        val lastRangeInMap = openOpen(5..7)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", lastRangeInMap to "R2D2")
        assertAll(
                { assertEquals(listOf(closedClosed(1..2) to "Yoda").toRangeMapEntryList(), sut[0..2])},
                { assertEquals(listOf(closedClosed(2..3) to "Yoda").toRangeMapEntryList(), sut[2..4])},
                { assertEquals(listOf(5 openClosed 6 to "R2D2").toRangeMapEntryList(), sut[4..6])},
                { assertEquals(listOf(closedOpen(6..7) to "R2D2").toRangeMapEntryList(), sut[6..8])}
        )
     }

    @Test
    fun get_returnsRangeInsideOneItemOfRangeMap() {
        val firstRangeInMap = closedClosed(1..4)
        val lastRangeInMap = openOpen(7..10)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", lastRangeInMap to "R2D2")
        for (range in listOf(closedClosed(1..2), openClosed(1..2), closedClosed(2..3),
                openClosed(2..3), closedOpen(2..3), closedClosed(2..3), openOpen(3..4),
                openClosed(3..4))) {
            assertEquals(listOf(range to "Yoda").toRangeMapEntryList(), sut[range])
        }
    }

    @Test
    fun get_complexExamples() {
        val firstRangeInMap = closedClosed(1..4)
        val secondRangeInMap = closedClosed(7..10)
        val lastRangeInMap = openOpen(14..16)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", secondRangeInMap to "Han Solo", lastRangeInMap to "R2D2")
        assertAll(
                {assertEquals(listOf(firstRangeInMap to "Yoda",
                        closedClosed(7..8) to "Han Solo").toRangeMapEntryList(), sut[0..8])} ,
                {assertEquals(listOf(closedClosed(3..4) to "Yoda",
                        secondRangeInMap to "Han Solo",
                        openClosed(14..15) to "R2D2").toRangeMapEntryList(), sut[3..15])} ,
                {assertEquals(listOf(openClosed(8..10) to "Han Solo",
                        openOpen(14..15) to "R2D2").toRangeMapEntryList(), sut[openOpen(8..15)])} ,
                {assertEquals(listOf(
                        openClosed(8..10) to "Han Solo",
                        lastRangeInMap to "R2D2").toRangeMapEntryList(), sut[openOpen(8..20)])}
        )
    }

    @Test
    fun get_overlapsOnOnePoint() {
        val firstRangeInMap = 1 closedClosed 4
        val secondRangeInMap = 7 closedClosed 10
        val lastRangeInMap = 14 openOpen 16
        val sut = rangeMapOf(firstRangeInMap to "Yoda", secondRangeInMap to "Han Solo", lastRangeInMap to "R2D2")
        assertAll(
                {assertEquals(listOf(closedClosed(4..4) to "Yoda",
                        closedClosed(7..7) to "Han Solo").toRangeMapEntryList(), sut[4..7])} ,
                {assertEquals(listOf(closedClosed(4..4) to "Yoda").toRangeMapEntryList(), sut[4..6])} ,
                {assertEquals(listOf(closedClosed(7..7) to "Han Solo").toRangeMapEntryList(), sut[5..7])}
        )
    }
}