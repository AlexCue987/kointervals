package org.kollektions.intervalmap.range

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AndWithInfinityTest {
    val lessThan1 = lessThan(1)
    val lessThan3 = lessThan(3)

    val noGreaterThan1 = noGreaterThan(1)
    val noGreaterThan3 = noGreaterThan(3)

    val greaterThan1 = greaterThan(1)
    val greaterThan3 = greaterThan(3)

    val noLessThan1 = noLessThan(1)
    val noLessThan3 = noLessThan(3)

    @Test
    fun lessThanToLessThan() {
        assertAll(
                { assertEquals(lessThan1, lessThan1.and(lessThan3)) },
                { assertEquals(lessThan1, lessThan1.and(lessThan1)) },
                { assertEquals(lessThan1, lessThan3.and(lessThan1)) }
        )
    }

    @Test
    fun lessThanToNoGreaterThan() {
        assertAll(
                { assertEquals(lessThan1, lessThan1.and(noGreaterThan3)) },
                { assertEquals(lessThan1, lessThan1.and(noGreaterThan1)) },
                { assertEquals(lessThan1, noGreaterThan3.and(lessThan1)) },
                { assertEquals(noGreaterThan1, lessThan3.and(noGreaterThan1)) },
                { assertEquals(noGreaterThan1, noGreaterThan1.and(lessThan3)) }
        )
    }

    @Test
    fun lessThanToNoLessThan() {
        assertAll(
                { assertEquals(1 closedOpen 3, lessThan3.and(noLessThan1))},
                { assertEquals(1 closedOpen 3, noLessThan1.and(lessThan3))},
                { assertFalse { lessThan1.and(noLessThan1).isNotEmpty() }},
                { assertFalse { noLessThan1.and(lessThan1).isNotEmpty() }}
        )
    }

    @Test
    fun lessThanToGreaterThan() {
        assertAll(
                { assertEquals(1 openOpen 3, lessThan3.and(greaterThan1))},
                { assertEquals(1 openOpen 3, greaterThan1.and(lessThan3))},
                { assertFalse { lessThan1.and(greaterThan1).isNotEmpty() }},
                { assertFalse { greaterThan1.and(lessThan1).isNotEmpty() }}
        )
    }

    @Test
    fun noGreaterThanToNoGreaterThan() {
        assertAll(
                { assertEquals(noGreaterThan1, noGreaterThan1.and(noGreaterThan3)) },
                { assertEquals(noGreaterThan1, noGreaterThan1.and(noGreaterThan1)) },
                { assertEquals(noGreaterThan1, noGreaterThan3.and(noGreaterThan1)) }
        )
    }

    @Test
    fun noGreaterThanToGreaterThan() {
        assertAll(
                { assertFalse(noGreaterThan1.and(greaterThan3).isNotEmpty()) },
                { assertFalse(greaterThan3.and(noGreaterThan1).isNotEmpty()) },
                { assertEquals(1 openClosed 3, noGreaterThan3.and(greaterThan1)) },
                { assertEquals(1 openClosed 3, greaterThan1.and(noGreaterThan3)) }
        )
    }

    @Test
    fun noGreaterThanToNoLessThan() {
        assertAll(
                { assertFalse(noGreaterThan1.and(noLessThan3).isNotEmpty()) },
                { assertFalse(noLessThan3.and(noGreaterThan1).isNotEmpty()) },
                { assertEquals(1 closedClosed  1, noGreaterThan1.and(noLessThan1)) },
                { assertEquals(1 closedClosed  1, noLessThan1.and(noGreaterThan1)) },
                { assertEquals(1 closedClosed  3, noGreaterThan3.and(noLessThan1)) },
                { assertEquals(1 closedClosed  3, noLessThan1.and(noGreaterThan3)) }
        )
    }

    @Test
    fun greaterThanToGreaterThan() {
        assertAll(
                { assertEquals(greaterThan3, greaterThan1.and(greaterThan3)) },
                { assertEquals(greaterThan1, greaterThan1.and(greaterThan1)) },
                { assertEquals(greaterThan3, greaterThan3.and(greaterThan1)) }
        )
    }
    
    @Test
    fun noLessThanToGreaterThan() {
        assertAll(
                { assertEquals(greaterThan3, noLessThan1.and(greaterThan3)) },
                { assertEquals(greaterThan1, noLessThan1.and(greaterThan1)) },
                { assertEquals(greaterThan3, greaterThan3.and(noLessThan1)) },
                { assertEquals(noLessThan3, noLessThan3.and(greaterThan1)) },
                { assertEquals(noLessThan3, greaterThan1.and(noLessThan3)) }
        )
    }

    @Test
    fun noLessThanToNoLessThan() {
        assertAll(
                { assertEquals(noLessThan3, noLessThan1.and(noLessThan3)) },
                { assertEquals(noLessThan1, noLessThan1.and(noLessThan1)) },
                { assertEquals(noLessThan3, noLessThan3.and(noLessThan1)) }
        )
    }
}