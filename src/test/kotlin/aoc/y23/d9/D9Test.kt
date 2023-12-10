@file:Suppress("unused")

package aoc.y23.d9

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D9Test {

    private fun p1() = D9P1()
    private fun p2() = D9P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(1993300041)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(1038)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(114)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(2)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "0 3 6 9 12 15,     18",
        "1 3 6 10 15 21,    28",
        "10 13 16 21 30 45, 68"
    ])
    fun line(line: String, result: Int) {
        val history = line.split(Regex("\\s+")).map { it.toInt() }
        assertThat(History(history).prediction).isEqualTo(result)
    }
}