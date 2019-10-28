package com.tgt.trans.common.range

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsBeforeNotAdjacentTest {
    val openOpen01 = 0 openOpen 1
    val openClosed01 = 0 openClosed 1
    val openClosed02 = 0 openClosed 2
    val openClosed13 = 1 openClosed 3
    val closedClosed13 = 1 closedClosed 3

    @Test
    fun falseIfNotBefore() {
        assertAll(
                { assertFalse { openClosed01.isBeforeNotAdjacent(closedClosed13) }},
                { assertFalse { openClosed02.isBeforeNotAdjacent(openClosed13) }}
        )
    }

    @Test
    fun falseIfBeforeButAdjacent() {
        assertAll(
                { assertFalse { openOpen01.isBeforeNotAdjacent(closedClosed13) }},
                { assertFalse { openClosed01.isBeforeNotAdjacent(openClosed13) }}
        )
    }

    @Test
    fun trueIfBeforeAndNotAdjacent() {
        assertFalse { openOpen01.endEdge.adjacentTo(openClosed13.startEdge()) }
        assertTrue(openOpen01.isBeforeNotAdjacent(openClosed13))
    }
}