package org.kollektions.intervalmap

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class TimesTest {
    val openOpen01 = openOpen(0..1)
    val openOpen04 = openOpen(0..4)
    val closedClosed12 = closedClosed(1..2)
    val openOpen12 = openOpen(1..2)
    val closedClosed13 = closedClosed(1..3)
    val openOpen13 = openOpen(1..3)
    val closedClosed23 = closedClosed(2..3)
    val closedOpen23 = closedOpen(2..3)
    val openOpen23 = openOpen(2..3)
    val openClosed23 = openClosed(2..3)
    val empty = emptyRange<Int>()

    @Test
    fun returnsEmptyIfNoOverlap() {
        assertAll(
                { assertFalse { (openOpen01.and(openOpen12)).isNotEmpty() }},
                { assertFalse { (openOpen12.and(openOpen01)).isNotEmpty() }},
                { assertFalse { (openOpen01.and(empty)).isNotEmpty() }},
                { assertFalse { (empty.and(openOpen01)).isNotEmpty() }},
                { assertFalse { (empty.and(empty)).isNotEmpty() }},
                { assertFalse { (openOpen01.and(closedClosed12)).isNotEmpty() }},
                { assertFalse { (closedClosed12.and(openOpen01)).isNotEmpty() }}
        )
    }

    @Test
    fun returnsRange() {
        assertAll(
                { assertEquals(openOpen23, openOpen13.and(openOpen23))} ,
                { assertEquals(openOpen23, openOpen23.and(openOpen13))} ,
                { assertEquals(closedOpen23, openOpen13.and(closedClosed23))} ,
                { assertEquals(closedOpen23, closedClosed23.and(openOpen13))} ,
                { assertEquals(closedClosed23, closedClosed13.and(closedClosed23))} ,
                { assertEquals(closedClosed23, closedClosed23.and(closedClosed13))} ,
                { assertEquals(openOpen23, openOpen04.and(openOpen23))} ,
                { assertEquals(openOpen23, openOpen23.and(openOpen04))} ,
                { assertEquals(openClosed23, openOpen04.and(openClosed23))} ,
                { assertEquals(openClosed23, openClosed23.and(openOpen04))} ,
                { assertEquals(closedOpen23, openOpen04.and(closedOpen23))} ,
                { assertEquals(closedOpen23, closedOpen23.and(openOpen04))} ,
                { assertEquals(closedClosed23, openOpen04.and(closedClosed23))} ,
                { assertEquals(closedClosed23, closedClosed23.and(openOpen04))}
        )
    }

    @Test
    fun rangeTimesSameRange() {
        for (range in listOf(openOpen23, openClosed23, closedOpen23, closedClosed23)) {
            assertEquals(range, range.and(range))
        }
    }

    @Test
    fun returnsOnePointRange(){
        assertEquals(closedClosed(2..2), closedClosed12.and(closedClosed23))
    }
}