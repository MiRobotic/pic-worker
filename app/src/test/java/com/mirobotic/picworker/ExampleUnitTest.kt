package com.mirobotic.picworker

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(22, Calculator.getSum(10,12))
        assertEquals(10, Calculator.getSum(5,5))
    }

    @Test
    fun add() {
        assertEquals(22, Calculator.getSum(10,12))
        assertEquals(10, Calculator.getSum(5,5))
    }

}
