package org.kollektions.intervalmap.range

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InfinityTest {
    val plusInfinity = PlusInfinity<Int>()
    val other = RangeEdge(1, true, EdgeSide.END)
    val minusInfinity = MinusInfinity<Int>()

    @Test
    fun compareToWorks() {
        assertAll(
                { assertEquals(0, plusInfinity.compareTo(plusInfinity)) },
                { assertEquals(0, minusInfinity.compareTo(minusInfinity)) },
                { assertTrue(minusInfinity < other) },
                { assertTrue(minusInfinity < plusInfinity) },
                { assertTrue(other < plusInfinity) }
        )
    }
}