package com.tgt.trans.common.range

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlusTest {
    val openOpen01 = 0 openOpen 1
    val openClosed01 = 0 openClosed 1
    val openOpen02 = 0 openOpen 2
    val openClosed02 = 0 openClosed 2
    val openOpen12 = 1 openOpen 2
    val closedOpen12 = 1 closedOpen 2
    val openOpen13 = 1 openOpen 3
    val openClosed13 = 1 openClosed 3
    val closedOpen13 = 1 closedOpen 3
    val closedClosed14 = 1 closedClosed 4
    val openClosed23 = 2 openClosed 3
    val openOpen23 = 2 openOpen 3
    val closedOpen23 = 2 closedOpen 3
    val closedClosed23 = 2 closedClosed  3
    val openOpen34 = 3 openOpen 4
    val closedOpen34 = 3 closedOpen 4

    @Test
    fun cannotAddIfAreNotAdjacent() {
        assertFailsWith(IllegalArgumentException::class) {
            (1 openOpen 2).or((2 openOpen 3))
        }
    }

    @Test
    fun mergesAdjacentRanges() {
        assertAll(
                { assertEquals(openOpen02, openClosed01.or(openOpen12)) },
                { assertEquals(openOpen02, openOpen12.or(openClosed01)) },
                { assertEquals(openOpen02, openOpen01.or(closedOpen12)) },
                { assertEquals(openOpen02, closedOpen12.or(openOpen01)) },
                { assertEquals(openOpen02, openClosed01.or(closedOpen12)) },
                { assertEquals(openOpen02, closedOpen12.or(openClosed01)) }
        )
    }

    @Test
    fun worksWhenRangeInsideAnother() {
        assertAll(
                { assertEquals(closedClosed14, closedClosed14.or(openClosed23))},
                { assertEquals(closedClosed14, openClosed23.or(closedClosed14))}
        )
    }

    @Test
    fun addsJustOnePointAtStart() {
        assertEquals(1 closedOpen 3, openOpen13.or((1 closedClosed 1)))
    }

    @Test
    fun addsJustOnePointAtEnd() {
        assertEquals(1 closedClosed 3, closedOpen13.or((3 closedClosed 3)))
    }
}