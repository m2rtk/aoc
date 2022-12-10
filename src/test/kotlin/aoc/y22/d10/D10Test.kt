@file:Suppress("unused")

package aoc.y22.d10

import aoc.run
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class D10Test {

    private val p1Solution: Any = 12980
    private val p2Solution: Any = "\n" + """
        ###..###....##.#....####.#..#.#....###..
        #..#.#..#....#.#....#....#..#.#....#..#.
        ###..#..#....#.#....###..#..#.#....#..#.
        #..#.###.....#.#....#....#..#.#....###..
        #..#.#.#..#..#.#....#....#..#.#....#....
        ###..#..#..##..####.#.....##..####.#....
    """.trimIndent()

    private fun p1() = D10P1()
    private fun p2() = D10P2()

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
        assertThat(p1().run("sample")).isEqualTo(13140)
    }

    @Test
    fun sample2() {
        assertThat(p2().run("sample")).isEqualTo(
            "\n" + """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
            """.trimIndent()
        )
    }
}