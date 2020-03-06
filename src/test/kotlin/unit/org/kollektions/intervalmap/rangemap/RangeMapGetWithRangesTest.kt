package org.kollektions.intervalmap.rangemap

import org.kollektions.intervalmap.range.closedClosed
import org.kollektions.intervalmap.range.closedOpen
import org.kollektions.intervalmap.range.openClosed
import org.kollektions.intervalmap.range.openOpen
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RangeMapGetWithRangesTest {
    @Test
    fun returnsNothingIfRangeBeforeMap(){
        val firstRangeInMap = closedClosed(1..3)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", closedClosed(4..5) to "R2D2")
        val range = openOpen(0..1)
        Assert.assertTrue("Guardian assumption", range.isBefore(firstRangeInMap))
        val actual = sut.getWithRanges(range)
        Assert.assertTrue(actual.isEmpty())
    }

    @Test
    fun returnsNothingIfRangeAfterMap(){
        val firstRangeInMap = closedClosed(1..3)
        val lastRangeInMap = 4 closedOpen 5
        val sut = rangeMapOf(firstRangeInMap to "Yoda", lastRangeInMap to "R2D2")
        val range = closedOpen(5..6)
        Assert.assertTrue("Guardian assumption", lastRangeInMap.isBefore(range))
        val actual = sut.getWithRanges(range)
        Assert.assertTrue(actual.isEmpty())
    }

    @Test
    fun returnsOneRangeOverlapped() {
        val firstRangeInMap = closedClosed(1..3)
        val lastRangeInMap = openOpen(5..7)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", lastRangeInMap to "R2D2")
        assertAll(
                { assertEquals(listOf(firstRangeInMap to "Yoda").toRangeMapEntryList(), sut.getWithRanges(0..2))},
                { assertEquals(listOf(firstRangeInMap to "Yoda").toRangeMapEntryList(), sut.getWithRanges(2..4))},
                { assertEquals(listOf(lastRangeInMap to "R2D2").toRangeMapEntryList(), sut.getWithRanges(4..6))},
                { assertEquals(listOf(lastRangeInMap to "R2D2").toRangeMapEntryList(), sut.getWithRanges(6..8))}
        )
     }

    @Test
    fun returnsRangeInsideOneItemOfRangeMap() {
        val firstRangeInMap = closedClosed(1..4)
        val lastRangeInMap = openOpen(7..10)
        val sut = rangeMapOf(firstRangeInMap to "Yoda", lastRangeInMap to "R2D2")
        for (range in listOf(closedClosed(1..2), openClosed(1..2), closedClosed(2..3),
                openClosed(2..3), closedOpen(2..3), closedClosed(2..3), openOpen(3..4),
                openClosed(3..4))) {
            assertEquals(listOf(firstRangeInMap to "Yoda").toRangeMapEntryList(), sut.getWithRanges(range))
        }
    }

    @Test
    fun complexExamples() {
        val firstRangeInMap = closedClosed(1..4)
        val secondRangeInMap = closedClosed(7..10)
        val lastRangeInMap = openOpen(14..16)
        val sut = rangeMapOf(firstRangeInMap to "Yoda",
                secondRangeInMap to "Han Solo",
                lastRangeInMap to "R2D2")
        assertAll(
                {assertEquals(listOf(firstRangeInMap to "Yoda",
                        secondRangeInMap to "Han Solo").toRangeMapEntryList(), sut.getWithRanges(0..8))} ,
                {assertEquals(listOf(firstRangeInMap to "Yoda",
                        secondRangeInMap to "Han Solo",
                        lastRangeInMap to "R2D2").toRangeMapEntryList(), sut.getWithRanges(3..15))} ,
                {assertEquals(listOf(secondRangeInMap to "Han Solo",
                        lastRangeInMap to "R2D2").toRangeMapEntryList(), sut.getWithRanges(openOpen(8..15)))} ,
                {assertEquals(listOf(
                        secondRangeInMap to "Han Solo",
                        lastRangeInMap to "R2D2").toRangeMapEntryList(), sut.getWithRanges(openOpen(8..20)))}
        )
    }

    @Test
    fun overlapsOnOnePoint() {
        val firstRangeInMap = 1 closedClosed 4
        val secondRangeInMap = 7 closedClosed 10
        val lastRangeInMap = 14 openOpen 16
        val sut = rangeMapOf(firstRangeInMap to "Yoda", secondRangeInMap to "Han Solo", lastRangeInMap to "R2D2")
        assertAll(
                {assertEquals(listOf(firstRangeInMap to "Yoda",
                        secondRangeInMap to "Han Solo").toRangeMapEntryList(), sut.getWithRanges(4..7))} ,
                {assertEquals(listOf(firstRangeInMap to "Yoda").toRangeMapEntryList(), sut.getWithRanges(4..6))} ,
                {assertEquals(listOf(secondRangeInMap to "Han Solo").toRangeMapEntryList(), sut.getWithRanges(5..7))}
        )
    }

    @Test
    fun returnsOptionalRange() {
        val firstRangeInMap = 1 closedClosed 4
        val secondRangeInMap = 7 closedClosed 10
        val lastRangeInMap = 14 openOpen 16
        val sut = rangeMapOf(firstRangeInMap to "Yoda", secondRangeInMap to "Han Solo", lastRangeInMap to "R2D2")
        for(point in 1..4) {
            assertEquals(RangeMapEntry(firstRangeInMap, "Yoda"), sut.getWithRange(point))
        }
    }

    @Test
    fun returnsOptionalEmpty() {
        val firstRangeInMap = 1 closedClosed 4
        val secondRangeInMap = 7 closedClosed 10
        val lastRangeInMap = 14 openOpen 16
        val sut = rangeMapOf(firstRangeInMap to "Yoda", secondRangeInMap to "Han Solo", lastRangeInMap to "R2D2")
        for(point in listOf(0, 5)) {
            assertTrue(sut.getWithRange(point) == null)
        }
    }
}