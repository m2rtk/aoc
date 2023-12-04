@file:Suppress("unused")

package aoc.y23.d2

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D2Test {

    private fun p1() = D2P1()
    private fun p2() = D2P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(2101)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(58269)
    }

    @Test
    fun example1() {
        assertThat(p1().run("sample")).isEqualTo(8)
    }
}