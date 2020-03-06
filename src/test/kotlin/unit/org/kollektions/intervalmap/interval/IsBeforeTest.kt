package org.kollektions.intervalmap

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsBeforeTest {
    private val openOpen01 = openOpen(0..1)
    private val closedClosed12 = closedClosed(1..2)
    private val openOpen12 = openOpen(1..2)
    private val closedClosed13 = closedClosed(1..3)
    private val openOpen13 = openOpen(1..3)
    private val closedClosed23 = closedClosed(2..3)
    private val openOpen23 = openOpen(2..3)
    private val empty = emptyRange<Int>()

    @Test
    fun isBefore_trueWithGap() {
        assertAll("Gap between ranges",
                { assertTrue { openOpen01.isBefore(openOpen23) } },
                { assertTrue { openOpen01.isBefore(closedClosed23) } }
        )
    }

    @Test
    fun isBefore_trueWithoutGap() {
        assertAll("No gap between ranges",
                { assertTrue { closedClosed12.isBefore(openOpen23) } },
                { assertTrue { openOpen12.isBefore(closedClosed23) } }
        )
    }

    @Test
    fun isBefore_false(){
        assertAll(
                { assertFalse {closedClosed12.isBefore(closedClosed23)} },
                { assertFalse { closedClosed12.isBefore(closedClosed13) }},
                { assertFalse { closedClosed12.isBefore(openOpen12) }},
                { assertFalse { openOpen12.isBefore(openOpen12) }},
                { assertFalse { openOpen12.isBefore(openOpen13) }}
        )
    }

    @Test
    fun isBefore_falseForEmpty(){
        assertAll(
                { assertFalse {closedClosed12.isBefore(empty)} },
                { assertFalse { empty.isBefore(closedClosed13) }},
                { assertFalse { openOpen12.isBefore(empty) }},
                { assertFalse { empty.isBefore(openOpen13) }}
        )
    }
}