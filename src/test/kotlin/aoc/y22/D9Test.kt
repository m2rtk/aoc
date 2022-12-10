@file:Suppress("unused")

package aoc.y22

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D9Test {

    private val p1Solution: Any = 5981
    private val p2Solution: Any = 2352

    private fun p1() = D9P1()
    private fun p2() = D9P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(p1Solution)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(p2Solution)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample2")).isEqualTo(36)
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = [
        // tail is touching head
        "4,4 + 5,5 = 4,4",
        "4,5 + 5,5 = 4,5",
        "4,6 + 5,5 = 4,6",
        "5,4 + 5,5 = 5,4",
        "5,5 + 5,5 = 5,5",
        "5,6 + 5,5 = 5,6",
        "6,4 + 5,5 = 6,4",
        "6,5 + 5,5 = 6,5",
        "6,6 + 5,5 = 6,6",

        // tail is non-diagonally detached
        "0,5 + 5,5 = 4,5",
        "2,5 + 5,5 = 4,5",
        "3,5 + 5,5 = 4,5",
        "7,5 + 5,5 = 6,5",
        "8,5 + 5,5 = 6,5",
        "9,5 + 5,5 = 6,5",
        "5,0 + 5,5 = 5,4",
        "5,2 + 5,5 = 5,4",
        "5,3 + 5,5 = 5,4",
        "5,7 + 5,5 = 5,6",
        "5,8 + 5,5 = 5,6",
        "5,9 + 5,5 = 5,6",

        // tail is diagonally detached
        "4,3 + 5,5 = 5,4",
        "4,7 + 5,5 = 5,6",
        "6,3 + 5,5 = 5,4",
        "6,7 + 5,5 = 5,6",

        "5,5 + 5,5 = 5,5",

    ])
    fun test(line: String) {
        val (tail, head, expected) = line
            .replace(Regex("\\s+"), "")
            .replace("(", "")
            .replace(")", "")
            .split(Regex("[+=]"), limit = 3)
            .map { Point(it.substringBefore(',').toInt(), it.substringAfter(',').toInt()) }

        assertThat(head.attach(tail)).isEqualTo(expected)
    }
}