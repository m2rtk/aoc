@file:Suppress("unused")

package aoc.y23.d7

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class D7Test {

    private fun p1() = D7P1()
    private fun p2() = D7P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(251806792L)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(252113488L)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(6440L)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(5905L)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "AAAAA, 7",
        "AA8AA, 6",
        "43334, 5",
        "33344, 5",
        "23332, 5",
        "33322, 5",
        "32323, 5",
        "5K355, 4",
        "12333, 4",
        "32133, 4",
        "TTT98, 4",
        "23432, 3",
        "A23A4, 2",
        "23456, 1",

        "32T3K, 2",
        "T55J5, 4",
        "KK677, 3",
        "KTJJT, 3",
        "QQQJA, 4",
    ])
    fun handTest(hand: String, value: Int) {
        assertThat(Hand(hand).type.value).isEqualTo(value)
    }
}