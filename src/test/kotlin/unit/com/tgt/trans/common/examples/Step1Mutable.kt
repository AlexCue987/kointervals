package com.tgt.trans.common.examples

import com.tgt.trans.common.range.closedClosed
import com.tgt.trans.common.range.closedOpen
import com.tgt.trans.common.range.openClosed
import com.tgt.trans.common.rangemap.RangeMapEntry
import com.tgt.trans.common.rangemap.mutableRangeMapFrom
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Step1Mutable {
    val sut = mutableRangeMapFrom(-30..10 to "Normal", 10..20 to "Mild", 20..30 to "Very Mild")

    @Test
    fun setsForValue() {
        val newValue = "Perfect"
        sut[15] = newValue
        assertEquals("Mild", sut[14], "Value Unchanged")
        assertEquals(newValue, sut[15], "Value Changed")
        assertEquals("Mild", sut[16], "Value Unchanged")
    }

    @Test
    fun setsForRange() {
        assertEquals("Very Mild", sut[30], "Guardian Assumption")
        val newValue = "Warm"
        sut[25..30] = newValue
        assertEquals(newValue, sut[30], "Value Changed")
    }

    @Test
    fun mergeRangesWithSameValue() {
        val sut = mutableRangeMapFrom(8..12 to "Work", 12..13 to "Lunch", 13..16 to "Work")
        sut[12..13] = "Work"
        val actual = sut.getWithRange(12)
        val expected = RangeMapEntry(8 closedClosed 16, "Work")
        assertEquals(expected, actual)
    }

    @Test
    fun removesForValue() {
        sut.remove(15)
        assertEquals("Mild", sut[14], "Value Unchanged")
        assertTrue(sut[15] == null, "Value Removed")
        assertEquals("Mild", sut[16], "Value Unchanged")
    }

    @Test
    fun removesForRange() {
        sut.remove(15..16)
        val expected = listOf(
                RangeMapEntry(10 closedOpen 15, "Mild"),
                RangeMapEntry(16 openClosed 19, "Mild")
        )
        assertEquals(expected, sut[10..19])
    }
}