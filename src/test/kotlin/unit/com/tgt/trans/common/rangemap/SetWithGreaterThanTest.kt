package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SetWithGreaterThanTest {
    val sut = mutableRangeMapOf(greaterThan(1) to "Rainy")

    @Test
    fun setsGreaterThanWithHigherValue() {
        sut[greaterThan(5)] = "Windy"
        val expected = rangeMapOf(
                1 openClosed 5 to "Rainy",
                greaterThan(5) to "Windy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsGreaterThanWithLowerValue() {
        sut[greaterThan(0)] = "Windy"
        val expected = rangeMapOf(
                greaterThan(0) to "Windy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoGreaterThanWithHigherValue() {
        sut[noGreaterThan(5)] = "Windy"
        val expected = rangeMapOf(
                noGreaterThan(5) to "Windy",
                greaterThan(5) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoGreaterThanWithLowerValue() {
        sut[noGreaterThan(0)] = "Windy"
        val expected = rangeMapOf(
                noGreaterThan(0) to "Windy",
                greaterThan(1) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsLessThanWithHigherValue() {
        sut[lessThan(5)] = "Windy"
        val expected = rangeMapOf(
                lessThan(5) to "Windy",
                noLessThan(5) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsLessThanWithLowerValue() {
        sut[lessThan(0)] = "Windy"
        val expected = rangeMapOf(
                lessThan(0) to "Windy",
                greaterThan(1) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoLessThanWithHigherValue() {
        sut[noLessThan(5)] = "Windy"
        val expected = rangeMapOf(
                1 openOpen 5 to "Rainy",
                noLessThan(5) to "Windy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoLessThanWithLowerValue() {
        sut[noLessThan(0)] = "Windy"
        val expected = rangeMapOf(
                noLessThan(0) to "Windy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsRange() {
        val windyPeriod = 1 closedClosed 3
        sut[windyPeriod] = "Windy"
        val expected = rangeMapOf(
                windyPeriod to "Windy",
                greaterThan(3) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsValueInside() {
        sut[3] = "Windy"
        val expected = rangeMapOf(
                1 openOpen 3 to "Rainy",
                3 closedClosed 3 to "Windy",
                greaterThan(3) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsValueOutside() {
        sut[-3] = "Windy"
        val expected = rangeMapOf(
                -3 closedClosed -3 to "Windy",
                greaterThan(1) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsAllValues() {
        sut[allValues()] = "Windy"
        val expected = rangeMapOf(
                allValues<Int>() to "Windy").toList()
        assertEquals(expected, sut.toList())
    }
}