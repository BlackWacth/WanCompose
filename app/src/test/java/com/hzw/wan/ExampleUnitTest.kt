package com.hzw.wan

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
        val num = 5
        when(num) {
            in 4..10 -> println("4..10")
            in 0..6 -> println("0..6")
        }
    }
}