@file:Suppress("unused")

package aoc.y22.d14

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D14Test {

    private val sample = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

    private val p1Solution: Any = 828
    private val p2Solution: Any = 25500

    private fun p1() = D14P1()
    private fun p2() = D14P2()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(p1Solution)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(p2Solution)
    }

    @Test
    fun sample1() {
        assertThat(p1().solve(sample)).isEqualTo(24)
    }

    @Test
    fun sample2() {
        assertThat(p2().solve(sample)).isEqualTo(93)
    }
}