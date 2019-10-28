package com.tgt.trans.common.range

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals

class AndWithAllValuesTest {
    val sut = allValues<Int>()
    val otherRanges = listOf(1 openClosed 3, lessThan(2), noLessThan(3), greaterThan(4), noGreaterThan(5))

    @Test
    fun allValuesAndOtherIsOther() {
        for(range in otherRanges) {
            assertAll(
                    { assertEquals(range, range.and(sut)) },
                    { assertEquals(range, sut.and(range)) }
            )
        }
    }
}