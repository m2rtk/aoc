@file:Suppress("unused")

package aoc.y15.d9

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D9Test {

    private fun p1() = D9P1()
    private fun p2() = D9P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(251)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(898)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(605)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(982)
    }
}