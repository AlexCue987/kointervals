package com.tgt.trans.common.examples

import com.tgt.trans.common.range.closedOpen
import com.tgt.trans.common.rangemap.RangeMapEntry
import com.tgt.trans.common.rangemap.rangeMapFrom
import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Step1GetWithRange {
    val sut = rangeMapFrom(-30..10 to "Normal", 10..20 to "Mild", 20..25 to "Very Mild")

    @Test
    fun getValue() {
        val expected = RangeMapEntry(10 closedOpen 20, "Mild")
        assertEquals(expected, sut.getWithRange(15))
    }

    @Test
    fun getValueMoreExamples() {
        for(degreesInF in 5..25 step 5) {
            println("$degreesInF is ${sut.getWithRange(degreesInF)}")
        }
    }

    @Test
    fun returnsEmpty() {
        assertAll(
                { assertTrue { sut.getWithRange(-31) == null } },
                { assertTrue { sut.getWithRange(26) == null } }
        )
    }
}