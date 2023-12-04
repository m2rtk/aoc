@file:Suppress("unused")

package aoc.y15.d8

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D8Test {

    private fun p1() = D8P1()
    private fun p2() = D8P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(1371)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(2117)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(12)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(19)
    }
}