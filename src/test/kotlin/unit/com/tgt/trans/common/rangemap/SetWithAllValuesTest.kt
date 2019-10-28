package com.tgt.trans.common.rangemap

import com.tgt.trans.common.range.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SetWithAllValuesTest {
    @Test
    fun setsGreaterThanToAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Rainy")
        sut[greaterThan(5)] = "Windy"
        val expected = mutableRangeMapOf(
                noGreaterThan(5) to "Rainy",
                greaterThan(5) to "Windy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoGreaterThanToAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Rainy")
        sut[noGreaterThan(5)] = "Windy"
        val expected = mutableRangeMapOf(
                noGreaterThan(5) to "Windy",
                greaterThan(5) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsLessThanToAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Rainy")
        sut[lessThan(5)] = "Windy"
        val expected = mutableRangeMapOf(
                lessThan(5) to "Windy",
                noLessThan(5) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsNoLessThanToAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Rainy")
        sut[noLessThan(5)] = "Windy"
        val expected = mutableRangeMapOf(
                lessThan(5) to "Rainy",
                noLessThan(5) to "Windy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsRangeToAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Rainy")
        val windyPeriod = 1 closedClosed 3
        sut[windyPeriod] = "Windy"
        val expected = mutableRangeMapOf(
                lessThan(1) to "Rainy",
                windyPeriod to "Windy",
                greaterThan(3) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }

    @Test
    fun setsValueToAllValues() {
        val sut = mutableRangeMapOf(allValues<Int>() to "Rainy")
        sut[1] = "Windy"
        val expected = mutableRangeMapOf(
                lessThan(1) to "Rainy",
                1 closedClosed 1 to "Windy",
                greaterThan(1) to "Rainy").toList()
        assertEquals(expected, sut.toList())
    }
}