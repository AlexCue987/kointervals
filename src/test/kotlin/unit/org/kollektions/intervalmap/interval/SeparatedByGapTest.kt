package org.kollektions.intervalmap

import org.junit.Test
import kotlin.test.assertTrue

class SeparatedByGapTest {
    @Test
    fun separatedByGap_trueForTwoOpenEdges() {
        assertTrue(openOpen(1..2).separatedByGap(2 openOpen 3))
    }
}