package org.kollektions.intervalmap

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AndTest {
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

    @Test
    fun greaterThan_and_rangeNotFullyInside() {
        val greaterThan1 = greaterThan(1)
        val closedClosed03 = 0 closedClosed 3
        assertAll(
                { assertEquals(1 openOpen 4, greaterThan1.and(openOpen04)) },
                { assertEquals(1 openOpen 4, openOpen04.and(greaterThan1)) },
                { assertEquals(1 openClosed 3, greaterThan1.and(closedClosed03)) },
                { assertEquals(1 openClosed 3, closedClosed03.and(greaterThan1)) }
        )
    }

    @Test
    fun greaterThan_and_rangeFullyInside() {
        val greaterThan0 = greaterThan(0)
        assertAll(
                { assertEquals(openOpen12, greaterThan0.and(openOpen12)) },
                { assertEquals(openOpen12, openOpen12.and(greaterThan0)) },
                { assertEquals(closedClosed12, greaterThan0.and(closedClosed12)) },
                { assertEquals(closedClosed12, closedClosed12.and(greaterThan0)) }
        )
    }

    @Test
    fun noLessThan_and_rangeNotFullyInside() {
        val noLessThan1 = noLessThan(1)
        val closedClosed03 = 0 closedClosed 3
        assertAll(
                { assertEquals(1 closedOpen 4, noLessThan1.and(openOpen04)) },
                { assertEquals(1 closedOpen 4, openOpen04.and(noLessThan1)) },
                { assertEquals(1 closedClosed 3, noLessThan1.and(closedClosed03)) },
                { assertEquals(1 closedClosed 3, closedClosed03.and(noLessThan1)) }
        )
    }

    @Test
    fun noLessThan_and_rangeFullyInside() {
        val noLessThan0 = noLessThan(0)
        assertAll(
                { assertEquals(openOpen12, noLessThan0.and(openOpen12)) },
                { assertEquals(openOpen12, openOpen12.and(noLessThan0)) },
                { assertEquals(closedClosed12, noLessThan0.and(closedClosed12)) },
                { assertEquals(closedClosed12, closedClosed12.and(noLessThan0)) }
        )
    }

    @Test
    fun lessThan_and_rangeNotFullyInside() {
        val lessThan2 = lessThan(2)
        val closedClosed03 = 0 closedClosed 3
        assertAll(
                { assertEquals(0 openOpen 2, lessThan2.and(openOpen04)) },
                { assertEquals(0 openOpen 2, openOpen04.and(lessThan2)) },
                { assertEquals(0 closedOpen 2, lessThan2.and(closedClosed03)) },
                { assertEquals(0 closedOpen 2, closedClosed03.and(lessThan2)) }
        )
    }

    @Test
    fun lessThan_and_rangeFullyInside() {
        val lessThan5 = lessThan(5)
        assertAll(
                { assertEquals(openOpen12, lessThan5.and(openOpen12)) },
                { assertEquals(openOpen12, openOpen12.and(lessThan5)) },
                { assertEquals(closedClosed12, lessThan5.and(closedClosed12)) },
                { assertEquals(closedClosed12, closedClosed12.and(lessThan5)) }
        )
    }

    @Test
    fun noGreaterThan_and_rangeNotFullyInside() {
        val noGreaterThan1 = noGreaterThan(1)
        val closedClosed03 = 0 closedClosed 3
        assertAll(
                { assertEquals(0 openClosed  1, noGreaterThan1.and(openOpen04)) },
                { assertEquals(0 openClosed  1, openOpen04.and(noGreaterThan1)) },
                { assertEquals(0 closedClosed 1, noGreaterThan1.and(closedClosed03)) },
                { assertEquals(0 closedClosed 1, closedClosed03.and(noGreaterThan1)) }
        )
    }

    @Test
    fun noGreaterThan_and_rangeFullyInside() {
        val noGreaterThan5 = noGreaterThan(5)
        assertAll(
                { assertEquals(openOpen12, noGreaterThan5.and(openOpen12)) },
                { assertEquals(openOpen12, openOpen12.and(noGreaterThan5)) },
                { assertEquals(closedClosed12, noGreaterThan5.and(closedClosed12)) },
                { assertEquals(closedClosed12, closedClosed12.and(noGreaterThan5)) }
        )
    }
}