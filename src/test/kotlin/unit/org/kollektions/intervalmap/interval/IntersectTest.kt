package org.kollektions.intervalmap

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.assertAll

class IntersectTest {
    val openOpen01 = openOpen(0..1)
    val openOpen03 = openOpen(0..3)
    val closedClosed12 = closedClosed(1..2)
    val openOpen12 = openOpen(1..2)
    val closedClosed13 = closedClosed(1..3)
    val openOpen13 = openOpen(1..3)
    val closedClosed23 = closedClosed(2..3)
    val openOpen23 = openOpen(2..3)
    val openOpen24 = openOpen(2..4)

    @Test
    fun falseWhenGapBetween() {
        assertAll(
                { Assert.assertFalse(openOpen01.intersect(closedClosed23)) },
                { Assert.assertFalse(closedClosed23.intersect(openOpen01)) }
        )
    }

    @Test
    fun falseWithoutGap() {
        assertAll(
                { Assert.assertFalse(openOpen01.intersect(openOpen12)) },
                { Assert.assertFalse(openOpen12.intersect(openOpen01)) },
                { Assert.assertFalse(openOpen01.intersect(closedClosed12)) },
                { Assert.assertFalse(closedClosed12.intersect(openOpen01)) },
                { Assert.assertFalse(closedClosed12.intersect(openOpen23)) },
                { Assert.assertFalse(openOpen23.intersect(closedClosed12)) }
        )
    }

    @Test
    fun trueWithoutGap() {
        assertAll(
                { Assert.assertTrue(closedClosed12.intersect(closedClosed23)) },
                { Assert.assertTrue(closedClosed23.intersect(closedClosed12)) }
        )
    }

    @Test
    fun trueWithOverlap() {
        assertAll(
                { Assert.assertTrue(openOpen13.intersect(openOpen23)) },
                { Assert.assertTrue(openOpen23.intersect(openOpen13)) },
                { Assert.assertTrue(openOpen13.intersect(openOpen24)) },
                { Assert.assertTrue(openOpen24.intersect(openOpen13)) }
        )
    }

    @Test
    fun trueWhenOneIntervalInsideAnother() {
        assertAll(
                { Assert.assertTrue(openOpen03.intersect(openOpen12)) },
                { Assert.assertTrue(openOpen12.intersect(openOpen03)) },
                { Assert.assertTrue(openOpen03.intersect(closedClosed12)) },
                { Assert.assertTrue(closedClosed12.intersect(openOpen03)) }
        )
    }
}