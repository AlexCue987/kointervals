package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.*
import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GapsTest {
    @Test
    fun noGapsInEmptyMap() {
        val sut = rangeMapOf<Int, Int>()
        assertTrue(sut.gaps().isEmpty())
    }

    @Test
    fun noGapsInOneRangeMap() {
        val sut = rangeMapOf(1 closedClosed 2 to 2)
        assertTrue(sut.gaps().isEmpty())
    }

    @Test
    fun noGapsIfRangesAdjacent() {
        val sut1 = rangeMapOf(1 closedClosed 2 to 2, 2 openClosed 3 to 3)
        val sut2 = rangeMapOf(1 closedOpen 2 to 2, 2 closedClosed 3 to 3)
        assertAll(
                { assertTrue(sut1.gaps().isEmpty()) },
                { assertTrue(sut2.gaps().isEmpty()) }
        )
    }

    @Test
    fun returnsOpenOpenGap() {
        val sut = rangeMapOf(1 closedClosed 2 to 2, 3 closedClosed 4 to 5)
        assertEquals(listOf(2 openOpen 3), sut.gaps())
    }

    @Test
    fun returnsOpenClosedGap() {
        val sut = rangeMapOf(1 closedClosed 2 to 2, 3 openClosed 4 to 5)
        assertEquals(listOf(2 openClosed  3), sut.gaps())
    }

    @Test
    fun returnsClosedOpenGap() {
        val sut = rangeMapOf(1 closedOpen 2 to 2, 3 closedClosed 4 to 5)
        assertEquals(listOf(2 closedOpen 3), sut.gaps())
    }

    @Test
    fun returnsClosedClosedGap() {
        val sut = rangeMapOf(1 closedOpen 2 to 2, 3 openClosed 4 to 5)
        assertEquals(listOf(2 closedClosed 3), sut.gaps())
    }

    @Test
    fun returnsOnePointGap() {
        val sut = rangeMapOf(1 closedOpen 3 to 2, 3 openClosed 4 to 5)
        assertEquals(listOf(3 closedClosed 3), sut.gaps())
    }

    @Test
    fun returnsSeveralGaps() {
        val sut = rangeMapOf(1 closedOpen 2 to 2, 3 openClosed 4 to 5, 5 openClosed 6 to 6)
        assertEquals(listOf(2 closedClosed 3, 4 openClosed 5), sut.gaps())
    }
}