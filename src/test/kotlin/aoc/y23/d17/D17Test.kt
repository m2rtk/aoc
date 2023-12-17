@file:Suppress("unused")

package aoc.y23.d17

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D17Test {

    private fun p1() = D17P1()
    private fun p2() = D17P2()

    private val sample = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent()

    @Test
    fun input1() {
        assertThat(p1().run("input")).isEqualTo(0)
    }

    @Test
    fun input2() {
        assertThat(p2().run("input")).isEqualTo(0)
    }

    @Test
    fun sample11() {
        assertThat(p1().solve(sample)).isEqualTo(102)
    }

    @Test
    fun sample12() {
        assertThat(p1().solve("""
            1111
            9991
            9991
            9991
        """.trimIndent())).isEqualTo(15)
    }

    @Test
    fun sample13() {
        assertThat(p1().solve("""
            1111
            9111
            9911
            9991
        """.trimIndent())).isEqualTo(7)
    }

    @Test
    fun sample14() {
        assertThat(p1().solve("""
            1119
            1921
            1191
            9111
        """.trimIndent())).isEqualTo(7)
    }

    @Test
    fun sample15() {
        assertThat(p1().solve("""
            9999
            9999
            9199
            9999
        """.trimIndent())).isEqualTo(6*9 + 1)
    }

    @Test
    fun sample16() {
        assertThat(p1().solve("""
            24134
            32154
            32552
            34465
        """.trimIndent())).isEqualTo(6*9 + 1)
    }

    @Test
    fun sample2() {
        assertThat(p2().solve(sample)).isEqualTo(0)
    }
}