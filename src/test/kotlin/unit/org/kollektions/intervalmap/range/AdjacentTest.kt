package org.kollektions.intervalmap.range

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AdjacentTest {
    val openStart1 = openStart(1)
    val closedStart1 = closedStart(1)
    val openEnd1 = openEnd(1)
    val closedEnd1 = closedEnd(1)

    @Test
    fun openStartNotAdjacentToOpenEnd() {
        assertAll(
                { assertFalse { openStart1.adjacentTo(openEnd1) }},
                { assertFalse { openEnd1.adjacentTo(openStart1) }}
        )
    }

    @Test
    fun adjacentIfAtLeastOneEdgeClosed() {
        assertAll(
                { assertTrue { openStart1.adjacentTo(closedEnd1) }},
                { assertTrue { closedEnd1.adjacentTo(openStart1) }},
                { assertTrue { closedStart1.adjacentTo(openEnd1) }},
                { assertTrue { openEnd1.adjacentTo(closedStart1) }},
                { assertTrue { closedStart1.adjacentTo(closedEnd1) }},
                { assertTrue { closedEnd1.adjacentTo(closedStart1) }}
        )
    }
}