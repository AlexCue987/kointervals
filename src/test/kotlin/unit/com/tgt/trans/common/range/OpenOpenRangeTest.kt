package com.tgt.trans.common.range

import org.junit.Assert
import org.junit.Test
import kotlin.test.assertFailsWith

class OpenOpenRangeTest {
    private val start = 1
    private val end = 5
    private val sut = openOpen(start .. end)

    @Test
    fun contains_beforeStart(){
        Assert.assertFalse(sut.contains(start - 1))
    }

    @Test
    fun contains_atStart(){
        Assert.assertFalse(sut.contains(start))
    }

    @Test
    fun contains_inside(){
        Assert.assertTrue(sut.contains(start + 1))
    }

    @Test
    fun contains_atEnd(){
        Assert.assertFalse(sut.contains(end))
    }

    @Test
    fun contains_afterEnd(){
        Assert.assertFalse(sut.contains(end + 1))
    }

    @Test
    fun cannotCreateEmpty(){
        assertFailsWith(IllegalArgumentException::class) {
            openOpen(1..1)
        }
    }
}