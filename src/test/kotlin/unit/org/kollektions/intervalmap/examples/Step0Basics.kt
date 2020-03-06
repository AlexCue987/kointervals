package org.kollektions.intervalmap.examples

import org.kollektions.intervalmap.map.rangeMapFrom
import org.junit.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Step0Basics {
    val sut = rangeMapFrom(-30..10 to "Normal", 10..20 to "Mild", 20..25 to "Very Mild")

    @Test
    fun getValue() {
        for(degreesInF in 5..25) {
            println("$degreesInF is ${sut[degreesInF]!!}")
        }
        assertAll(
                { assertEquals ("Normal", sut[9]) },
                { assertEquals ("Mild", sut[10]) }
        )
    }

    @Test
    fun returnsEmpty() {
        assertAll(
                { assertTrue { sut[-31] == null } },
                { assertTrue { sut[26] == null } }
        )
    }
}