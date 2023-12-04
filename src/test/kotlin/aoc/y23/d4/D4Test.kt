@file:Suppress("unused")

package aoc.y23.d4

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D4Test {

    private fun p1() = D4P1()
    private fun p2() = D4P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(15268)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(6283755)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(13)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(30)
    }
}