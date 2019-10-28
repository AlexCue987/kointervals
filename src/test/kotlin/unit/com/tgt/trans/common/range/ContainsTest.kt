package com.tgt.trans.common.range

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.assertAll

class ContainsTest {
    private val start = 1
    private val middle = 2
    private val end = 3
    private val closedClosed13 = closedClosed(1..3)
    private val closedOpen13 = closedOpen(1..3)
    private val openOpen13 = openOpen(1..3)
    private val openClosed13 = openClosed(1..3)

    @Test
    fun falseBeforeStart() {
        val beforeStart = 0
        assertAll(
                { Assert.assertFalse(beforeStart in closedOpen13) },
                { Assert.assertFalse(beforeStart in closedClosed13) },
                { Assert.assertFalse(beforeStart in openOpen13) },
                { Assert.assertFalse(beforeStart in openClosed13) }
        )
    }

    @Test
    fun falseAtStart() {
        assertAll(
                { Assert.assertFalse(openOpen13.contains(start))} ,
                { Assert.assertFalse(openClosed13.contains(start))}
        )
    }

    @Test
    fun trueAtStart() {
        assertAll(
                { Assert.assertTrue(closedOpen13.contains(start))} ,
                { Assert.assertTrue(closedClosed13.contains(start))}
        )
    }

    @Test
    fun trueInTheMiddle() {
        assertAll(
                { Assert.assertTrue(closedOpen13.contains(middle)) },
                { Assert.assertTrue(closedClosed13.contains(middle)) },
                { Assert.assertTrue(openOpen13.contains(middle)) },
                { Assert.assertTrue(openClosed13.contains(middle)) }
        )
    }

    @Test
    fun falseAtEnd() {
        assertAll(
                { Assert.assertFalse(openOpen13.contains(end))} ,
                { Assert.assertFalse(closedOpen13.contains(end))}
        )
    }

    @Test
    fun trueAtEnd() {
        assertAll(
                { Assert.assertTrue(openClosed13.contains(end))} ,
                { Assert.assertTrue(closedClosed13.contains(end))}
        )
    }

    @Test
    fun falseAfterEnd() {
        val afterEnd = 4
        assertAll(
                { Assert.assertFalse(closedOpen13.contains(afterEnd)) },
                { Assert.assertFalse(closedClosed13.contains(afterEnd)) },
                { Assert.assertFalse(openOpen13.contains(afterEnd)) },
                { Assert.assertFalse(openClosed13.contains(afterEnd)) }
        )
    }
}