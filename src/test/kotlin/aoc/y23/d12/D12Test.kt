@file:Suppress("unused")

package aoc.y23.d12

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.math.exp

class D12Test {

    private fun p1() = D12P1()
    private fun p2() = D12P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(0)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(0)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(0)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(0)
    }

    @ParameterizedTest
    @CsvSource(delimiter = '-', value = [
        "???.### 1,1,3                  - 1",
        ".??..??...?##. 1,1,3           - 4",
        "?#?#?#?#?#?#?#? 1,3,1,6        - 1",
        "????.#...#... 4,1,1            - 1",
        "????.######..#####. 1,6,5      - 4",
        "?###???????? 3,2,1             - 10",
    ])
    fun test(input: String, expected: Int) {
        assertThat(p1().solve(input)).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(delimiter = '-', value = [
        "#.#.### 1,1,3                  - true",
        ".#....#...###. 1,1,3           - true",
        ".#.#.###.###### 1,3,1,6        - false",
        ".#.###.#.###### 1,3,1,6        - true",
        "####.#...#... 4,1,1            - true",
        "#....######..#####. 1,6,5      - true",
        ".###.##.#... 3,2,1             - true",
        "####.##.#... 3,2,1             - false",
    ])
    fun testIsOk(input: String, expected: Boolean) {
        val i = input.parseLine()
        assertThat(isOk(i.springs, i.goodSpringGroups)).isEqualTo(expected)
    }
}