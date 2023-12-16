@file:Suppress("unused")

package aoc.y23.d16

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D16Test {

    private fun p1() = D16P1()
    private fun p2() = D16P2()

    private val sample = """
            .|...\....
            |.-.\.....
            .....|-...
            ........|.
            ..........
            .........\
            ..../.\\..
            .-.-/..|..
            .|....-|.\
            ..//.|....
        """.trimIndent()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(7728)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(8061)
    }

    @Test
    fun sample1() {
        assertThat(p1().solve(sample)).isEqualTo(46)
    }

    @Test
    fun sample2() {
        assertThat(p2().solve(sample)).isEqualTo(51)
    }
}