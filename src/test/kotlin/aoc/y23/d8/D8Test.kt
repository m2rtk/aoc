@file:Suppress("unused")

package aoc.y23.d8

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.math.BigInteger

class D8Test {

    private fun p1() = D8P1()
    private fun p2() = D8P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(19667L)
    }

    @Test
//    @Disabled
    fun input2() {
        assertThat(p2().run("input")).isEqualTo((19185263738117L).toBigInteger())
    }

    @Test
    fun sample11() {
        assertThat(p1().run("sample1")).isEqualTo(2L)
    }
    @Test
    fun sample12() {
        assertThat(p1().run("sample2")).isEqualTo(6L)
    }

    @Test
    fun sample3() {
        assertThat(p2().run("sample3")).isEqualTo(BigInteger("6"))
    }
}