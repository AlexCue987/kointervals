package org.kollektions.intervalmap.examples

import org.kollektions.intervalmap.*
import org.kollektions.intervalmap.map.rangeMapOf
import org.junit.Test
import kotlin.test.assertEquals

class Step3Infinities {
    val lowValue = "Low"
    val highValue = "High"
    val threshold = 20

    @Test
    fun greaterThanExample() {
        val sut = rangeMapOf(noGreaterThan(threshold) to lowValue,
                greaterThan(threshold) to highValue)
        assertEquals(lowValue, sut[threshold-1])
        assertEquals(lowValue, sut[threshold])
        assertEquals(highValue, sut[threshold+1])
    }

    @Test
    fun lessThanExample() {
        val sut = rangeMapOf(lessThan(threshold) to lowValue,
                noLessThan(threshold) to highValue)
        assertEquals(lowValue, sut[threshold-1])
        assertEquals(highValue, sut[threshold])
        assertEquals(highValue, sut[threshold+1])
    }

    @Test
    fun allValuesExample() {
        val sut = rangeMapOf(allValues<Int>() to lowValue, threshold closedClosed threshold to highValue)
        assertEquals(lowValue, sut[threshold-1])
        assertEquals(highValue, sut[threshold])
        assertEquals(lowValue, sut[threshold+1])
    }
}