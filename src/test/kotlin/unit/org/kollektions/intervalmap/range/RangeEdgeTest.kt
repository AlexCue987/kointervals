package org.kollektions.intervalmap.range

import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RangeEdgeTest {
    @Test
    fun inverse() {
        val inclusiveStart = RangeEdge(1, true, EdgeSide.START)
        val inclusiveEnd = RangeEdge(1, true, EdgeSide.END)
        val exclusiveStart = RangeEdge(1, false, EdgeSide.START)
        val exclusiveEnd = RangeEdge(1, false, EdgeSide.END)
        assertAll(
                { assertEquals(inclusiveEnd, exclusiveStart.inverse()) },
                { assertEquals(exclusiveStart, inclusiveEnd.inverse()) },
                { assertEquals(inclusiveStart, exclusiveEnd.inverse()) },
                { assertEquals(exclusiveEnd, inclusiveStart.inverse()) }
        )
    }

    @Test
    fun compareToDifferentValues() {
        for(start in EdgeSide.values()) {
            for(end in EdgeSide.values()) {
                val one = RangeEdge(1, false, start)
                val two = RangeEdge(2, false, end)
                assertAll(
                        { assertEquals(-1, one.compareTo(two)) },
                        { assertEquals(1, two.compareTo(one)) }
                )
            }
        }
    }

    @Test
    fun compareToStartToEnd() {
        val inclusiveOneStart = RangeEdge(1, true, EdgeSide.START)
        val exclusiveOneStart = RangeEdge(1, false, EdgeSide.START)
        val inclusiveOneEnd = RangeEdge(1, true, EdgeSide.END)
        val exclusiveOneEnd = RangeEdge(1, false, EdgeSide.END)
        assertAll(
                { assertEquals(0, inclusiveOneStart.compareTo(inclusiveOneEnd)) },
                { assertEquals(0, inclusiveOneEnd.compareTo(inclusiveOneStart)) },
                { assertEquals(1, exclusiveOneStart.compareTo(exclusiveOneEnd)) },
                { assertEquals(-1, exclusiveOneEnd.compareTo(exclusiveOneStart)) },
                { assertEquals(1, inclusiveOneStart.compareTo(exclusiveOneEnd)) },
                { assertEquals(-1, inclusiveOneEnd.compareTo(exclusiveOneStart)) },
                { assertEquals(1, exclusiveOneStart.compareTo(inclusiveOneEnd)) },
                { assertEquals(-1, exclusiveOneEnd.compareTo(inclusiveOneStart)) }
        )
    }

    @Test
    fun compareToStartToStart() {
        val inclusiveOneStart = RangeEdge(1, true, EdgeSide.START)
        val exclusiveOneStart = RangeEdge(1, false, EdgeSide.START)
        assertAll(
                { assertEquals(0, inclusiveOneStart.compareTo(inclusiveOneStart)) },
                { assertEquals(-1, inclusiveOneStart.compareTo(exclusiveOneStart)) },
                { assertEquals(1, exclusiveOneStart.compareTo(inclusiveOneStart)) },
                { assertEquals(0, exclusiveOneStart.compareTo(exclusiveOneStart)) }
        )
    }

    @Test
    fun compareToEndToEnd() {
        val inclusiveOneEnd = RangeEdge(1, true, EdgeSide.END)
        val exclusiveOneEnd = RangeEdge(1, false, EdgeSide.END)
        assertAll(
                { assertEquals(0, inclusiveOneEnd.compareTo(inclusiveOneEnd)) },
                { assertEquals(1, inclusiveOneEnd.compareTo(exclusiveOneEnd)) },
                { assertEquals(-1, exclusiveOneEnd.compareTo(inclusiveOneEnd)) },
                { assertEquals(0, exclusiveOneEnd.compareTo(exclusiveOneEnd)) }
        )
    }

    @Test
    fun compareInclusiveToValue(){
        val inclusiveOneStart = RangeEdge(1, true, EdgeSide.START)
        val inclusiveOneEnd = RangeEdge(1, true, EdgeSide.END)
        assertAll(
                { assertTrue { inclusiveOneStart > 0 }},
                { assertTrue { inclusiveOneStart <= 1 }},
                { assertTrue { inclusiveOneStart >= 1 }},
                { assertTrue { inclusiveOneStart < 2 }},
                { assertTrue { inclusiveOneEnd > 0 }},
                { assertTrue { inclusiveOneEnd <= 1 }},
                { assertTrue { inclusiveOneEnd >= 1 }},
                { assertTrue { inclusiveOneEnd < 2 }}
        )
    }

    @Test
    fun compareExclusiveToValue(){
        val exclusiveOneStart = RangeEdge(1, false, EdgeSide.START)
        val exclusiveOneEnd = RangeEdge(1, false, EdgeSide.END)
        assertAll(
                { assertTrue { exclusiveOneStart > 0 }},
                { assertTrue { exclusiveOneStart > 1 }},
                { assertTrue { exclusiveOneStart < 2 }},
                { assertTrue { exclusiveOneEnd > 0 }},
                { assertTrue { exclusiveOneEnd < 1 }},
                { assertTrue { exclusiveOneEnd < 2 }}
        )
    }
}