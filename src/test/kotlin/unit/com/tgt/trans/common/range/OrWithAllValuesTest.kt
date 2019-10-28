package com.tgt.trans.common.range

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals

class OrWithAllValuesTest {
    val sut = allValues<Int>()
    val otherRanges = listOf(1 openClosed 3, lessThan(2), noLessThan(3), greaterThan(4), noGreaterThan(5))

    @Test
    fun allValuesOrOtherIsAllValues() {
        for(range in otherRanges) {
            assertAll(
                    { assertEquals(sut, range.or(sut)) },
                    { assertEquals(sut, sut.or(range)) }
            )
        }
    }
}