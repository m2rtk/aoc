@file:Suppress("unused")

package aoc.y23.d1

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D1Test {

    private fun p1() = D1P1()
    private fun p2() = D1P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(53334)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(52834)
    }
    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(281)
    }

    @ParameterizedTest // this does not pass BUT I got the correct answer with my input
    @CsvSource(value = [
        "twone, 21",
        "5vvlxrhtdtwoneq, 51"
    ])
    fun line2(input: String, output: Int) {
        assertThat(p2().solve(input)).isEqualTo(output)
    }
}