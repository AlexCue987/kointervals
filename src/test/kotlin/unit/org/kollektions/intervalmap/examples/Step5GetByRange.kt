package org.kollektions.intervalmap.examples

import org.kollektions.intervalmap.closedClosed
import org.kollektions.intervalmap.closedOpen
import org.kollektions.intervalmap.map.RangeMapEntry
import org.kollektions.intervalmap.map.rangeMapFrom
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Step5GetByRange {
    val sut = rangeMapFrom(-30..10 to "Normal", 10..20 to "Mild", 20..25 to "Very Mild")

    @Test
    fun get() {
        val expected = listOf(
                RangeMapEntry(5 closedOpen 10, "Normal"),
                RangeMapEntry(10 closedClosed 15, "Mild")
                )
        assertEquals(expected, sut[5..15])
    }

    @Test
    fun getWithRanges() {
        val expected = sut.toList().take(2)
        assertEquals(expected, sut.getWithRanges(5..15))
    }

    @Test
    fun returnsEmpty() {
        assertTrue { sut[30..40].isEmpty() }
    }
}