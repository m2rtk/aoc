@file:Suppress("unused")

package aoc.y23.d3

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D3Test {

    private fun p1() = D3P1()
    private fun p2() = D3P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(540212)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(87605697)
    }

    @Test
    fun example1() {
        assertThat(p1().run("sample")).isEqualTo(4361)
    }
    @Test
    fun example2() {
        assertThat(p2().run("sample")).isEqualTo(467835)
    }
}