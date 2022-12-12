@file:Suppress("unused")

package aoc.y22.d12

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D12Test {

    private val sample: String = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi 
    """.trimIndent()

    private val p1Solution: Any = 472
    private val p2Solution: Any = 465

    private fun p1() = D12P1()
    private fun p2() = D12P2()

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
        val result = p1().solve(sample).also { println(it) }

        assertThat(result).isEqualTo(31)
    }
    @Test
    fun sample2() {
        val result = p2().solve(sample).also { println(it) }

        assertThat(result).isEqualTo(29)
    }
}