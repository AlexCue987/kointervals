package org.kollektions.intervalmap.range

import org.kollektions.intervalmap.rangemap.rangeMapOf
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class GetByPointTest {
    val sut = rangeMapOf(1 closedOpen 3 to "Windy")

    @Test
    fun getReturnsValue(){
        val expected = sut.toList()[0]
        for(point in 1..2) {
            val actual = sut[point]
            assertEquals(expected.value, actual)
        }
    }

    @Test
    fun getOrDefaultReturnsValue(){
        val expected = sut.toList()[0].value
        for(point in 1..2) {
            val default = "Sunny"
            assertFalse("Guardian assumption") {expected == default}
            val actual = sut.getOrDefault(point, default)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getReturnsEmpty(){
        for(point in listOf(0, 3)) {
            val actual = sut[point]
            assertEquals(null, actual)
        }
    }

    @Test
    fun getOrDefaultReturnsDefault(){
        val default = "Sunny"
        for(point in listOf(0, 3)) {
             val actual = sut.getOrDefault(point, default)
            assertEquals(default, actual)
        }
    }
}