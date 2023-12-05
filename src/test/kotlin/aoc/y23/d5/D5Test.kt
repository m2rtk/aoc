@file:Suppress("unused")

package aoc.y23.d5

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D5Test {

    private fun p1() = D5P1()
    private fun p2() = D5P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(600279879L)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(20191102L)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(35L)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(46L)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "52, 50, 48, 79, 81",
        "0, 0, 1, 81, -",
        "18, 25, 70, 81, 74"
    ], nullValues = ["-"])
    fun testEntry(destStart: Long, sourceStart: Long, rangeLength: Long, input: Long, expected: Long?) {
        assertThat(ConversionMapEntry(destStart, sourceStart, rangeLength).invoke(input)).isEqualTo(expected)
    }
}