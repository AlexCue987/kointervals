package org.kollektions.intervalmap.examples

import org.kollektions.intervalmap.closedClosed
import org.kollektions.intervalmap.closedOpen
import org.kollektions.intervalmap.openClosed
import org.kollektions.intervalmap.openOpen
import org.kollektions.intervalmap.map.rangeMapFrom
import org.kollektions.intervalmap.map.rangeMapOf
import org.junit.jupiter.api.assertAll
import java.math.BigDecimal
import java.math.BigDecimal.*
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class Step3RangeTypes {
    val zero01 = BigDecimal("0.01")
    val zero99 = BigDecimal("0.99")
    val expectedValue = "Expected Value"

    @Test
    fun openOpenExample() {
        val sut = rangeMapOf(ZERO openOpen ONE to expectedValue)
        assertAll(
                { assertTrue { sut[ZERO] == null } },
                { assertEquals(expectedValue, sut[zero01]) },
                { assertEquals(expectedValue, sut[zero99]) },
                { assertTrue { sut[ONE] == null } }
        )
    }

    @Test
    fun openClosedExample() {
        val sut = rangeMapOf(ZERO openClosed ONE to expectedValue)
        assertAll(
                { assertTrue { sut[ZERO] == null } },
                { assertEquals(expectedValue, sut[zero01]) },
                { assertEquals(expectedValue, sut[zero99]) },
                { assertEquals(expectedValue, sut[ONE]) }
        )
    }

    @Test
    fun closedOpenExample() {
        val sut = rangeMapOf(ZERO closedOpen ONE to expectedValue)
        assertAll(
                { assertEquals(expectedValue, sut[ZERO]) },
                { assertEquals(expectedValue, sut[zero01]) },
                { assertEquals(expectedValue, sut[zero99]) },
                { assertTrue { sut[ONE] == null } }
        )
    }

    @Test
    fun closedClosedExample() {
        val mapFromClosedClosed = rangeMapOf(ZERO closedClosed ONE to expectedValue)
        val mapFromStandardRangeIsTheSameAsAbove = rangeMapFrom(ZERO..ONE to expectedValue)
        for(sut in listOf(mapFromClosedClosed, mapFromStandardRangeIsTheSameAsAbove)) {
            assertAll(
                    { assertEquals(expectedValue, sut[ZERO]) },
                    { assertEquals(expectedValue, sut[zero01]) },
                    { assertEquals(expectedValue, sut[zero99]) },
                    { assertEquals(expectedValue, sut[ONE]) }
            )
        }
    }
}