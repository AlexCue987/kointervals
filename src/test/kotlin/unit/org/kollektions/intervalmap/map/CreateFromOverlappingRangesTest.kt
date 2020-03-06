package org.kollektions.intervalmap.map

import org.kollektions.intervalmap.closedClosed
import org.kollektions.intervalmap.closedOpen
import org.kollektions.intervalmap.openOpen
import org.junit.Test
import kotlin.test.assertEquals


class CreateFromOverlappingRangesTest {
    private val expected = listOf(1 closedOpen 2 to 3, 2 closedClosed 3 to 4, 3 openOpen 4 to 3, 4 closedClosed 6 to 4)
            .map { RangeMapEntry(it.first, it.second) }

    @Test
    fun createsMutableFromOverlappingRanges() {
        val sut = mutableRangeMapOf(1 closedClosed 3 to 3,
                2 closedClosed 6 to 4, 3 openOpen 4 to 3)
        assertEquals(expected, sut.toList())
    }

    @Test
    fun createsFromOverlappingRanges() {
        val sut = rangeMapOf(1 closedClosed 3 to 3,
                2 closedClosed 6 to 4, 3 openOpen 4 to 3)
        assertEquals(expected, sut.toList())
    }
}
