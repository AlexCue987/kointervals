package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SetWithLessThanTest {
    val originalRange = lessThan(1)
    val originalValue = "Rainy"
    val newValue = "Windy"
    val sut = mutableRangeMapOf(originalRange to originalValue)

    @Test
    fun setsGreaterThanWithHigherValue() {
        val newRange = greaterThan(5)
        sut[newRange] = newValue
        val expected = rangeMapOf(
                originalRange to originalValue,
                newRange to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsGreaterThanWithLowerValue() {
        val newRange = greaterThan(0)
        sut[newRange] = newValue
        val expected = rangeMapOf(
                noGreaterThan(0) to originalValue,
                newRange to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoGreaterThanWithHigherValue() {
        val newRange = noGreaterThan(5)
        sut[newRange] = newValue
        val expected = rangeMapOf(
                newRange to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoGreaterThanWithLowerValue() {
        val newRange = noGreaterThan(0)
        sut[newRange] = newValue
        val expected = rangeMapOf(
                newRange to newValue,
                0 openOpen 1 to originalValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsLessThanWithHigherValue() {
        val newRange = lessThan(5)
        sut[newRange] = newValue
        val expected = rangeMapOf(newRange to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsLessThanWithLowerValue() {
        sut[lessThan(0)] = newValue
        val expected = rangeMapOf(
                lessThan(0) to newValue,
                0 closedOpen 1 to originalValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoLessThanWithHigherValue() {
        val newRange = noLessThan(5)
        sut[newRange] = newValue
        val expected = rangeMapOf(
                originalRange to originalValue,
                newRange to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoLessThanWithLowerValue() {
        val newRange = noLessThan(0)
        sut[newRange] = newValue
        val expected = rangeMapOf(
                lessThan(0) to originalValue,
                newRange to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsRange() {
        val windyPeriod = -3 closedClosed 0
        sut[windyPeriod] = newValue
        val expected = rangeMapOf(
                lessThan(-3) to originalValue,
                windyPeriod to newValue,
                0 openOpen 1 to originalValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsValueInside() {
        sut[-3] = newValue
        val expected = rangeMapOf(
                lessThan(-3) to originalValue,
                -3 closedClosed -3 to newValue,
                -3 openOpen 1 to originalValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsValueOutside() {
        sut[5] = newValue
        val expected = rangeMapOf(
                originalRange to originalValue,
                5 closedClosed 5 to newValue).toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsAllValues() {
        sut[allValues()] = newValue
        val expected = rangeMapOf(
                allValues<Int>() to newValue).toList()
        assertEquals(expected, sut.toList())
    }
}