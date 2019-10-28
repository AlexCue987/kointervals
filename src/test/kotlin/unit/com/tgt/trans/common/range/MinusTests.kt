package com.tgt.trans.common.range

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

class MinusTests {
    val openOpen01 = 0 openOpen 1
    val openClosed01 = 0 openClosed 1
    val openOpen02 = 0 openOpen 2
    val openClosed02 = 0 openClosed 2
    val openOpen13 = 1 openOpen 3
    val openClosed13 = 1 openClosed 3
    val closedOpen13 = 1 closedOpen 3
    val closedClosed13 = 1 closedClosed  3
    val openClosed23 = 2 openClosed 3
    val openOpen23 = 2 openOpen 3
    val closedOpen23 = 2 closedOpen 3
    val closedClosed23 = 2 closedClosed  3
    val openOpen34 = 3 openOpen 4
    val closedOpen34 = 3 closedOpen 4

    @Test
    fun minusReturnsSameIfOtherBeforeWithOpenEnd() {
        for(range in listOf(openOpen13, openClosed13, closedOpen13, closedClosed13)) {
            assertEquals(listOf(range), range - openOpen01)
        }
    }

    @Test
    fun minusReturnsSameIfOtherBeforeWithClosedEnd() {
        for(range in listOf(openOpen13, openClosed13)) {
            assertEquals(listOf(range), range - openClosed01)
        }
    }

    @Test
    fun minusReturnsSameIfOtherAfterWithOpenStart() {
        for(range in listOf(openOpen13, openClosed13, closedOpen13, closedClosed13)) {
            assertEquals(listOf(range), range - openOpen34)
        }
    }

    @Test
    fun minusReturnsSameIfOtherAfterWithClosedStart() {
        for(range in listOf(openOpen13, closedOpen13)) {
            assertEquals(listOf(range), range - closedOpen34)
        }
    }

    @Test
    fun minusRemovesInclusiveStart() {
        assertAll(
                { assertEquals(listOf(openClosed13), closedClosed13 - openClosed01)},
                { assertEquals(listOf(openOpen13), closedOpen13 - openClosed01)}
        )
    }

    @Test
    fun minusRemovesInclusiveEnd() {
        assertAll(
                { assertEquals(listOf(closedOpen13), closedClosed13 - closedOpen34)},
                { assertEquals(listOf(openOpen13), openClosed13 - closedOpen34)}
        )
    }

    @Test
    fun minusRemovesRangeAtStart() {
        assertAll(
                { assertEquals(listOf(openClosed23), closedClosed13 - openClosed02)},
                { assertEquals(listOf(openOpen23), closedOpen13 - openClosed02)},
                { assertEquals(listOf(closedClosed23), closedClosed13 - openOpen02)},
                { assertEquals(listOf(closedOpen23), closedOpen13 - openOpen02)},
                { assertEquals(listOf(closedClosed23), closedClosed13 - (1 closedOpen 2))},
                { assertEquals(listOf(closedOpen23), closedOpen13 - (1 closedOpen 2))}
        )
    }
}