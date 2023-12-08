@file:Suppress("unused")

package aoc.y23.d6

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D6Test {

    private fun p1() = D6P1()
    private fun p2() = D6P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(211904)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(43364472)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(288)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(71503)
    }
}