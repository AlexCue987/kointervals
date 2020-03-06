package org.kollektions.intervalmap.rangemap

import org.kollektions.intervalmap.range.closedClosed
import org.kollektions.intervalmap.range.closedOpen
import org.kollektions.intervalmap.range.openClosed
import org.kollektions.intervalmap.range.openOpen
import org.junit.Test
import kotlin.test.assertEquals

class MergeAdjacentItemsWithSameValuesTest {
    @Test
    fun handlesOneItem() {
        val element = RangeMapEntry(openOpen(1..2), "Cold")
        val actual = mergeAdjacentItemsWithSameValues(listOf(element))
        assertEquals(listOf(element), actual)
    }

    @Test
    fun handlesTwoItemsWithDifferentValues() {
        val morning = RangeMapEntry(openClosed(7..12), "Cold")
        val afternoon = RangeMapEntry(openOpen(12..18), "Warm")
        val actual = mergeAdjacentItemsWithSameValues(listOf(morning, afternoon))
        assertEquals(listOf(morning, afternoon), actual)
    }

    @Test
    fun keepsTwoItemsWithSameValueSeparateIfNotAdjacent() {
        val sameValue = "Cold"
        val morning = RangeMapEntry(openOpen(7..12), sameValue)
        val afternoon = RangeMapEntry(openOpen(12..18), sameValue)
        val actual = mergeAdjacentItemsWithSameValues(listOf(morning, afternoon))
        assertEquals(listOf(morning, afternoon), actual)
    }

    @Test
    fun mergesTwoItemsWithSameValue() {
        val sameValue = "Cold"
        val morning = RangeMapEntry(openClosed(7..12), sameValue)
        val afternoon = RangeMapEntry(openOpen(12..18), sameValue)
        val actual = mergeAdjacentItemsWithSameValues(listOf(morning, afternoon))
        assertEquals(listOf(RangeMapEntry(7 openOpen 18, sameValue)), actual)
    }

    @Test
    fun complexExample() {
        val earlyMorning = RangeMapEntry(openOpen(7..9), "Cold")
        val lateMorning = RangeMapEntry(closedOpen(9..11), "Cold")
        val morning = RangeMapEntry(openOpen(7..11), "Cold")
        val lunchtime = RangeMapEntry(closedClosed(11..12), "Indoors")
        val earlyAfternoon = RangeMapEntry(openClosed(12..16), "Warm")
        val lateAfternoon = RangeMapEntry(closedOpen(16..18), "Warm")
        val afternoon = RangeMapEntry(openOpen(12..18), "Warm")
        val beforeMerging = listOf(earlyMorning, lateMorning, lunchtime, earlyAfternoon, lateAfternoon)
        val actual = mergeAdjacentItemsWithSameValues(beforeMerging)
        assertEquals(listOf(morning, lunchtime, afternoon), actual)
    }
}