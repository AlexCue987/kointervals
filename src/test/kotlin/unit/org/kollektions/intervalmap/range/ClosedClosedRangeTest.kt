package org.kollektions.intervalmap.range

import org.junit.Assert
import org.junit.Test

class ClosedClosedRangeTest {
    private val start = 1
    private val end = 5
    private val sut = closedClosed(start .. end)

    @Test
    fun contains_beforeStart(){
        Assert.assertFalse(sut.contains(start - 1))
    }

    @Test
    fun contains_atStart(){
        Assert.assertTrue(sut.contains(start))
    }

    @Test
    fun contains_inside(){
        Assert.assertTrue(sut.contains(start + 1))
    }

    @Test
    fun contains_atEnd(){
        Assert.assertTrue(sut.contains(end))
    }

    @Test
    fun contains_afterEnd(){
        Assert.assertFalse(sut.contains(end + 1))
    }

    @Test
    fun startCanEqualEnd(){
        val startSameAsEnd = 1
        val sut = closedClosed(startSameAsEnd..startSameAsEnd)
        Assert.assertTrue(sut.contains(startSameAsEnd))
    }
}