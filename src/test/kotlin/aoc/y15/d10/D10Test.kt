@file:Suppress("unused")

package aoc.y15.d10

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D10Test {

    private fun p1() = D10P1()
    private fun p2() = D10P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(329356)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(4666278)
    }

    @ParameterizedTest
    @CsvSource(
        "1, 1, 2",
        "1, 2, 2",
        "1, 3, 4",
        "1, 4, 6",
        "1, 5, 6",
    )
    fun sample1(input: String, times: Int, expected: Int) {
        assertThat(naive(input, times)).isEqualTo(expected)
    }
}