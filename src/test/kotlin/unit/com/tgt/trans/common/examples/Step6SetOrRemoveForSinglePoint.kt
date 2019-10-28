package com.tgt.trans.common.examples

import com.tgt.trans.common.rangemap.mutableRangeMapFrom
import org.junit.Test
import kotlin.test.assertEquals

class Step6SetOrRemoveForSinglePoint {
    val sut = mutableRangeMapFrom(-30..10 to "Normal", 10..20 to "Mild", 20..25 to "Very Mild")

    @Test
    fun setsValue() {
        sut[15] = "Perfect"
        assertEquals("Perfect", sut[15])
    }

    @Test
    fun removesValue() {
        sut.remove(15)
        assertEquals(null, sut[15])
    }
}