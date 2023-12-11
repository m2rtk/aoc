@file:Suppress("unused")

package aoc.y23.d11

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class D11Test {

    private fun p1() = D11P1()
    private fun p2() = D11P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(9608724)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(904633799472L)
    }

    @Test
    fun sample1() {
        assertThat(p1().run("sample")).isEqualTo(374)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(82000210L)
    }
}