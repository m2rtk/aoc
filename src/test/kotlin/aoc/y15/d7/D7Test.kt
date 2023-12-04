@file:Suppress("unused")

package aoc.y15.d7

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D7Test {

    private fun p1() = D7P1()
    private fun p2() = D7P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo((16076).toUShort())
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo((2797).toUShort())
    }

    @Test
    fun sample1() {
        p1().run("sample")
    }
}