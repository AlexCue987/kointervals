package org.kollektions.intervalmap

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OrTest {

    @Test
    fun blowsUpIfNotAdjacent() {
        val open1to3 = 1 openOpen 3
        val open3to5 = 3 openOpen 5
        assertAll(
                { assertFailsWith<IllegalArgumentException> { open1to3.or(open3to5) } },
                { assertFailsWith<IllegalArgumentException> { open3to5.or(open1to3) } }
        )
    }

    @Test
    fun mergesAdjacent() {
        assertAll(
                { assertEquals(1 openOpen 5, (1 openClosed 3).or(3 openOpen 5))},
                { assertEquals(1 openOpen 5, (1 openOpen 3).or(3 closedOpen 5))},
                { assertEquals(1 openOpen 5, (3 openOpen 5).or(1 openClosed 3))},
                { assertEquals(1 openOpen 5, (3 closedOpen 5).or(1 openOpen 3))}
        )
    }

    @Test
    fun mergesOverlapping() {
        assertAll(
                { assertEquals(1 openOpen 5, (1 openClosed 4).or(3 openOpen 5))},
                { assertEquals(1 openOpen 5, (1 openOpen 4).or(3 closedOpen 5))},
                { assertEquals(1 openOpen 5, (3 openOpen 5).or(1 openClosed 4))},
                { assertEquals(1 openOpen 5, (3 closedOpen 5).or(1 openOpen 4))}
        )
    }

    @Test
    fun mergesOneInsideAnother() {
        assertEquals(1 openOpen 5, (1 openOpen 5).or(2 openOpen 4))
    }
}